from rest_framework import serializers
from .models import Item, Transaction
from django.contrib.auth.models import User

class ItemSerializer(serializers.ModelSerializer):
    class Meta:
        model = Item
        fields = ['id', 'name', 'price', 'status', 'owner']

class TransactionSerializer(serializers.ModelSerializer):
    class Meta:
        model = Transaction
        fields = ['id', 'item', 'buyer', 'seller', 'is_approved']

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields =['id', 'username', 'email', 'first_name', 'last_name']