from django.shortcuts import render
from rest_framework import viewsets, permissions, generics
from rest_framework.decorators import action
from rest_framework.response import Response
from .models import Item, ItemStatus, Transaction, User
from .serializers import ItemSerializer, TransactionSerializer, UserSerializer

class ItemViewSet(viewsets.ModelViewSet):
    queryset = Item.objects.all()
    serializer_class = ItemSerializer

    def get_permissions(self):
        if self.action in ['list', 'retrive']:
            permission_classes = [permissions.AllowAny]
        else:
            permission_classes = [permissions.IsAuthenticated]
        return [permission() for permission in permission_classes]

    @action(detail=True, methods=['post'], permission_classes=[permissions.IsAuthenticated])
    def purchase(self, request, pk=None):
        item = self.get_object()
        if item.status != ItemStatus.FOR_SALE:
            return Response({'status': 'Item not for sale'}, status=400)

        transaction = Transaction.objects.create(
            item=item,
            buyer=request.user,
            seller=item.owner
        )
        item.status = ItemStatus.RESERVED
        item.save()
        return Response({'status': 'Purchase initiated'})

class TransactionViewSet(viewsets.ModelViewSet):
    queryset = Transaction.objects.all()
    serializer_class = TransactionSerializer
    permission_classes = [permissions.IsAuthenticated]

    @action(detail=True, methods=['post'])
    def approve(self, request, pk=None):
        transaction = self.get_object()
        if transaction.seller != request.user:
            return Response({'status':'Not allowed'}, status=403)
        transaction.is_approved = True
        transaction.item.status = ItemStatus.COMPLETED
        transaction.item.save()
        transaction.save()
        return Response({'status': 'Transaction approved'})


class UserViewSet(viewsets.ModelViewSet):
    queryset = User.objects.all()
    serializer_class = UserSerializer
    permission_classes = [permissions.IsAuthenticated]