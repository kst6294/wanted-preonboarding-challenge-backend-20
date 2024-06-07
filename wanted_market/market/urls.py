from django.contrib import admin
from django.urls import path, include
from rest_framework.routers import DefaultRouter
from market.views import ItemViewSet, TransactionViewSet, UserViewSet

router = DefaultRouter()
router.register(r'items', ItemViewSet)
router.register(r'transaction', TransactionViewSet)
router.register(r'users', UserViewSet)

urlpatterns = [
    path('api/', include(router.urls)),
    path('api/auth/', include('rest_framework.urls', namespace='rest_framework')),
]