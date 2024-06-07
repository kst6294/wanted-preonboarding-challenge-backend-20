from django.db import models
from django.utils.translation import gettext_lazy as _

# Create your models here.

# User는 django 기본 유저 사용 => get_user_model() or AUTH_USER_MODEL
class Order(models.Model):
    class Status(models.TextChoices):
        Sale = "sl", _("판매중")
        Reservations = "rv", _("예약중")
        completed = "cp", _("완료")
    
    user = models.ForeignKey
    product_name = models.CharField(verbose_name = '모델명', max_length=150)
    price = models.DecimalField(verbose_name='가격', max_digits=5, decimal_places=2)
    reservation_status = models.CharField(max_length=2, choices=Status.choices, default=Status.Sale)
