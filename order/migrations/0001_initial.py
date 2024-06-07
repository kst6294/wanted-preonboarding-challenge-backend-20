# Generated by Django 4.2.13 on 2024-06-07 15:16

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Order',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('product_name', models.CharField(max_length=150, verbose_name='모델명')),
                ('price', models.DecimalField(decimal_places=2, max_digits=5, verbose_name='가격')),
                ('reservation_status', models.CharField(choices=[('sl', '판매중'), ('rv', '예약중'), ('cp', '완료')], default='sl', max_length=2)),
            ],
        ),
    ]
