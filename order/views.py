from rest_framework.permissions import IsAuthenticatedOrReadOnly, IsAuthenticated
from rest_framework.response import Response
from rest_framework import status
from rest_framework import generics
from django_filters import rest_framework as filters
from django.db import transaction
from django.db.models import F, Q
from .models import Order
from .serializers import OrderSerializer, OrderDetailSerializer
from .permissions import SellerPermission
# Create your views here.

# 1. 제품 등록(회원) 및 목록 조회(비회원)
class ListCreateOrder(generics.ListCreateAPIView):
    queryset = Order.objects.all()
    permission_classes = [IsAuthenticatedOrReadOnly]
    serializer_class = OrderSerializer
    filter_backends = (filters.DjangoFilterBackend,)
    filterset_fields = ('reservation_status', 'reservation_status')
    
    @transaction.atomic
    def perform_create(self, serializer):
        serializer.save(seller=self.request.user)

# 2. 제품 구매
class RetrieveUpdateOrder(generics.RetrieveUpdateAPIView):
    queryset = Order.objects.all()
    permission_classes = [IsAuthenticatedOrReadOnly]
    serializer_class = OrderDetailSerializer
    filter_backends = (filters.DjangoFilterBackend,)
    filterset_fields = ('reservation_status', 'reservation_status')

    def retrieve(self, request, *args, **kwargs):
        instance = self.get_object()
        
        if not self.request.user.is_authenticated:
            self.serializer_class = OrderSerializer
        
        serializer = self.get_serializer(instance)
        return Response(serializer.data)
    
    @transaction.atomic
    def update(self, request, *args, **kwargs):
        price = self.request.query_params.get("price", default=None)
        partial = kwargs.pop('partial', False)
        instance = self.get_object()
        
        if instance.seller == self.request.user:
            return Response("자신이 등록한 제품은 구매할 수 없습니다.", status=status.HTTP_400_BAD_REQUEST)
        
        if self.request.user.id in instance.buyer.values_list('id', flat=True) and (instance.reservation_status == "판매중" or instance.reservation_status == "예약중"):
            return Response("이미 예약되었습니다. 취소하시겠습니까?", status=status.HTTP_400_BAD_REQUEST)
        
        if self.request.user.id in instance.buyer.values_list('id', flat=True) and instance.reservation_status == "완료":
            return Response("이미 구매된 상품입니다.", status=status.HTTP_400_BAD_REQUEST)
        
        if price and instance.price is not price:
            return Response("상품의 가격이 변경되었습니다. 다시 확인해 주세요.", status=status.HTTP_400_BAD_REQUEST)
        
        serializer = self.get_serializer(instance, data=request.data, partial=partial)
        serializer.is_valid(raise_exception=True)
        self.perform_update(serializer)
        instance.buyer.add(self.request.user)

        instance.quantity = F("quantity") - 1
        instance.save()
        instance.refresh_from_db()

        if instance.quantity == 0:
            instance.reservation_status = "예약중"
            instance.save()

        if getattr(instance, '_prefetched_objects_cache', None):
            # If 'prefetch_related' has been applied to a queryset, we need to
            # forcibly invalidate the prefetch cache on the instance.
            instance._prefetched_objects_cache = {}

        return Response(serializer.data)
    
    def perform_update(self, serializer):
        serializer.save()

# 3. 등록된 제품의 조회(목록, 상세)

# 4. 거래내역 조회 >> # 1로 이동

# 5. 내 정보(구매중, 예약중)

# 6. 판매 승인(판매자)
class ConfirmOrder(generics.UpdateAPIView):
    queryset = Order.objects.all()
    permission_classes = [SellerPermission]
    serializer_class = OrderDetailSerializer
    filter_backends = (filters.DjangoFilterBackend,)
    filterset_fields = ('reservation_status', 'reservation_status')
    
    def update(self, request, *args, **kwargs):
        partial = kwargs.pop('partial', False)
        instance = self.get_object()
        
        if not instance.buyer.exists():
            return Response("구매자가 존재해야 합니다.", status=status.HTTP_400_BAD_REQUEST)
        
        if self.request.user.id in instance.buyer.values_list('id', flat=True):
            return Response("상품을 등록한 판매자만 승인을 할 수 있습니다.", status=status.HTTP_400_BAD_REQUEST)
        
        serializer = self.get_serializer(instance, data=request.data, partial=partial)
        serializer.is_valid(raise_exception=True)
        self.perform_update(serializer)

        if getattr(instance, '_prefetched_objects_cache', None):
            # If 'prefetch_related' has been applied to a queryset, we need to
            # forcibly invalidate the prefetch cache on the instance.
            instance._prefetched_objects_cache = {}

        return Response(serializer.data)
    
    @transaction.atomic
    def perform_update(self, serializer):
        serializer.save(reservation_status="완료")
        
class SellerProductsList(generics.ListAPIView):
    permission_classes = [IsAuthenticated]
    serializer_class = OrderDetailSerializer
    filter_backends = (filters.DjangoFilterBackend,)
    filterset_fields = ('reservation_status', 'reservation_status')
    
    def list(self, request, *args, **kwargs):
        self.queryset = Order.objects.filter(seller = self.request.user, reservation_status = "예약중")
        queryset = self.filter_queryset(self.get_queryset())

        page = self.paginate_queryset(queryset)
        if page is not None:
            serializer = self.get_serializer(page, many=True)
            return self.get_paginated_response(serializer.data)

        serializer = self.get_serializer(queryset, many=True)
        return Response(serializer.data)
    
class BuyerProductsList(generics.ListAPIView):
    permission_classes = [IsAuthenticated]
    serializer_class = OrderDetailSerializer
    
    def list(self, request, *args, **kwargs):
        self.queryset = Order.objects.filter(buyer=self.request.user).filter(Q(reservation_status="예약중") | Q(reservation_status="판매중"))
        print(self.queryset)
        queryset = self.filter_queryset(self.get_queryset())

        page = self.paginate_queryset(queryset)
        if page is not None:
            serializer = self.get_serializer(page, many=True)
            return self.get_paginated_response(serializer.data)

        serializer = self.get_serializer(queryset, many=True)
        return Response(serializer.data)