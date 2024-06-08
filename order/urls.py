from django.urls import path

from . import views

urlpatterns = [
    path("checkout/orders/", views.ListCreateOrder.as_view(), name="list_create_order"),
    path("checkout/orders/<int:pk>/", views.RetrieveUpdateOrder.as_view(), name="retreive_update_order"),
    path("checkout/orders/<int:pk>/confirm/", views.ConfirmOrder.as_view(), name="confirm_order"),
    path("catalogs/products/seller/", views.SellerProductsList.as_view(), name="product_list_seller"),
    path("catalogs/products/buyer/", views.BuyerProductsList.as_view(), name="product_list_buyer"),
]