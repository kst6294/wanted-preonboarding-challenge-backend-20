from django.urls import path

from . import views

urlpatterns = [
    path("checkout/orders/", views.ListCreateOrder.as_view(), name="list_create_order"),
    path("checkout/orders/<int:pk>/buyer/", views.RetrieveUpdateOrderBuyer.as_view(), name="retreive_update_order_buyer"),
    path("checkout/orders/<int:pk>/seller/", views.RetrieveUpdateOrderSeller.as_view(), name="retreive_update_order_seller"),
]