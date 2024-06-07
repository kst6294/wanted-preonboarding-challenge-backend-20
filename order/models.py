from django.db import models
from users.models import Member
from django.utils.translation import gettext_lazy as _

# Create your models here.

# User는 django 기본 유저 사용 => get_user_model() or AUTH_USER_MODEL
class Order(models.Model):
    class Status(models.TextChoices):
        Sale = "판매중"
        Reservations = "예약중"
        completed = "완료"
    
    seller = models.ForeignKey(Member, on_delete = models.CASCADE, related_name='seller')
    product_name = models.CharField(verbose_name = '모델명', max_length=150)
    price = models.DecimalField(verbose_name='가격', max_digits=6, decimal_places=2)
    reservation_status = models.CharField(max_length=3, choices=Status.choices, default=Status.Sale)
    buyer = models.ForeignKey(Member, on_delete = models.SET_NULL, related_name='buyer', null=True, blank=True)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)