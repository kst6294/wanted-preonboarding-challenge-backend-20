from django.db import models
from django.contrib.auth.models import User

class ItemStatus(models.TextChoices):
    FOR_SALE = '판매중', '판매중'
    RESERVED = '예약중', '예약중'
    COMPLETED = '완료', '완료'

class Item(models.Model):
    name = models.CharField(max_length=255)
    price = models.PositiveIntegerField()
    status = models.CharField(
        max_length=10,
        choices=ItemStatus.choices,
        default=ItemStatus.FOR_SALE
    )
    owner = models.ForeignKey(User, related_name='items', on_delete=models.CASCADE)

class Transaction(models.Model):
    item = models.ForeignKey(Item, related_name='transactions', on_delete=models.CASCADE)
    buyer = models.ForeignKey(User, related_name='purchases', on_delete=models.CASCADE)
    seller = models.ForeignKey(User, related_name='sales', on_delete=models.CASCADE)
    is_approved = models.BooleanField(default=False)