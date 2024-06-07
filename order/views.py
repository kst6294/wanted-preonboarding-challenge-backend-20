from rest_framework.permissions import IsAuthenticatedOrReadOnly
from rest_framework.response import Response
from rest_framework import status
from rest_framework import generics
from django_filters import rest_framework as filters
from .models import Order
from .serializers import OrderSerializer
# Create your views here.

# 1. 제품 등록 및 목록 조회
class ListCreateOrder(generics.ListCreateAPIView):
    queryset = Order.objects.all()
    permission_classes = [IsAuthenticatedOrReadOnly]
    serializer_class = OrderSerializer
    filter_backends = (filters.DjangoFilterBackend,)
    filterset_fields = ('reservation_status', 'reservation_status')
    
    def get(self, request, *args, **kwargs):
        print(self.queryset)
        return self.list(request, *args, **kwargs)
    
    def perform_create(self, serializer):
        serializer.save(seller=self.request.user)

# 2. 제품 구매(구매자)
class RetrieveUpdateOrderBuyer(generics.RetrieveUpdateAPIView):
    queryset = Order.objects.all()
    permission_classes = [IsAuthenticatedOrReadOnly]
    serializer_class = OrderSerializer
    filter_backends = (filters.DjangoFilterBackend,)
    filterset_fields = ('reservation_status', 'reservation_status')
    
    def update(self, request, *args, **kwargs):
        partial = kwargs.pop('partial', False)
        instance = self.get_object()
        if instance.seller == self.request.user:
            return Response("자신이 등록한 제품은 구매할 수 없습니다.", status=status.HTTP_400_BAD_REQUEST)
        
        if instance.buyer == self.request.user and instance.reservation_status == "완료":
            return Response("이미 구매된 상품입니다.", status=status.HTTP_400_BAD_REQUEST)
        
        serializer = self.get_serializer(instance, data=request.data, partial=partial)
        serializer.is_valid(raise_exception=True)
        self.perform_update(serializer)

        if getattr(instance, '_prefetched_objects_cache', None):
            # If 'prefetch_related' has been applied to a queryset, we need to
            # forcibly invalidate the prefetch cache on the instance.
            instance._prefetched_objects_cache = {}

        return Response(serializer.data)
    
    def perform_update(self, serializer):
        serializer.save(buyer=self.request.user)

# 3. 등록된 제품의 조회(목록, 상세)

# 4. 거래내역 조회 >> # 1로 이동

# 5. 내 정보(구매중, 예약중)

# 6. 판매 승인(판매자)
class RetrieveUpdateOrderSeller(generics.RetrieveUpdateAPIView):
    queryset = Order.objects.all()
    permission_classes = [IsAuthenticatedOrReadOnly]
    serializer_class = OrderSerializer
    filter_backends = (filters.DjangoFilterBackend,)
    filterset_fields = ('reservation_status', 'reservation_status')
    
    def update(self, request, *args, **kwargs):
        partial = kwargs.pop('partial', False)
        instance = self.get_object()
        
        if instance.buyer is None:
            return Response("구매자가 존재해야 합니다.", status=status.HTTP_400_BAD_REQUEST)
        
        if instance.buyer == self.request.user:
            return Response("상품을 등록한 판매자만 승인을 할 수 있습니다.", status=status.HTTP_400_BAD_REQUEST)
        
        serializer = self.get_serializer(instance, data=request.data, partial=partial)
        serializer.is_valid(raise_exception=True)
        self.perform_update(serializer)

        if getattr(instance, '_prefetched_objects_cache', None):
            # If 'prefetch_related' has been applied to a queryset, we need to
            # forcibly invalidate the prefetch cache on the instance.
            instance._prefetched_objects_cache = {}

        return Response(serializer.data)
    
    def perform_update(self, serializer):
        serializer.save(reservation_status="완료")