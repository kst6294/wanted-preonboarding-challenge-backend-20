from rest_framework import permissions

class SellerPermission(permissions.BasePermission):
    def has_object_permission(self, request, view, obj):
        if obj.seller == request.user:
            return True
        return False