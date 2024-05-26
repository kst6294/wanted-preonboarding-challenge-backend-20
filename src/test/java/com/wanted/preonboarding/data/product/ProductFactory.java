package com.wanted.preonboarding.data.product;

import com.wanted.preonboarding.data.users.UsersModuleHelper;
import com.wanted.preonboarding.module.product.dto.CreateProduct;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.user.entity.Users;

public class ProductFactory {

    public static Product generateProduct(CreateProduct createProduct) {
        Users usersWithId = UsersModuleHelper.toUsersWithId();
        Product product = ProductModuleHelper.toProductWithId(createProduct);
        product.setSeller(usersWithId);
        return product;
    }

}
