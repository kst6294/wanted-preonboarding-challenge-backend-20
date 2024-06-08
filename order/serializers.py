from rest_framework import serializers
from .models import Order

# 회원용
class OrderDetailSerializer(serializers.ModelSerializer):
    class Meta:
        model = Order
        fields = '__all__'
        read_only_fields = ['seller']
        
# 비회원용
class OrderSerializer(serializers.ModelSerializer):
    class Meta:
        model = Order
        exclude = ['buyer']
        # read_only_fields = ['seller']