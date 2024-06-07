from django.test import TestCase
from django.urls import reverse
from django.contrib.auth.models import User
from rest_framework.test import APIClient
from rest_framework import status
from .models import Item, Transaction
from rest_framework_simplejwt.tokens import RefreshToken

class ItemModelTest(TestCase):
    def setUp(self):
        self.user = User.objects.create_user(username='testuser', password='1234567')
        self.item = Item.objects.create(name='Test Item', price=1000, owner=self.user)

    def test_item_creation(self):
        self.assertEqual(self.item.name, 'Test Item')
        self.assertEqual(self.item.price, 1000)
        self.assertEqual(self.item.owner, self.user)

class TransationModelTest(TestCase):
    def setUp(self):
        self.buyer = User.objects.create_user(username='buyer', password='1234567')
        self.seller = User.objects.create_user(username='seller', password='1234567')
        self.item = Item.objects.create(name='Test Item', price=1000, owner=self.seller)
        self.transaction = Transaction.objects.create(item=self.item, buyer=self.buyer, seller=self.seller)

    def test_transaction_creation(self):
        self.assertEqual(self.transaction.item, self.item)
        self.assertEqual(self.transaction.buyer, self.buyer)
        self.assertEqual(self.transaction.seller, self.seller)


class MarketAPITest(TestCase):
    def setUp(self):
        self.client = APIClient()
        self.user = User.objects.create_user(username='testuser', password='1234567')
        self.token = RefreshToken.for_user(self.user)
        self.client.credentials(HTTP_AUTHORIZATION = f'Bearer {self.token.access_token}')
        self.item = Item.objects.create(name='Test Item', price=1000, owner=self.user)

    def test_item_liset(self):
        response = self.client.get(reverse('item-list'))
        self.assertEqual(response.status_code, status.HTTP_200_OK)

    def test_item_detail(self):
        response = self.client.get(reverse('item-detail', args=[self.item.id]))
        self.assertEqual(response.status_code, status.HTTP_200_OK)

    def test_item_create(self):
        data = {'name': 'New Item', 'price':2000, 'owner': self.user.id}
        response = self.client.post(reverse('item-list'), data, format='json')
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
    
    def test_item_purchase(self):
        response = self.client.post(reverse('item-purchase', args=[self.item.id]))
        self.assertEqual(response.status_code, status.HTTP_200_OK)

    def test_transaction_approve(self):
        transaction = Transaction.objects.create(item=self.item, buyer=self.user, seller=self.user)
        response = self.client.post(reverse('transaction-approve', args=[transaction.id]))
        self.assertEqual(response.status_code, status.HTTP_200_OK)