package com.wanted.preonboarding.data.product;

import com.wanted.preonboarding.data.users.UsersModuleHelper;
import com.wanted.preonboarding.module.product.core.BaseSku;
import com.wanted.preonboarding.module.product.core.Sku;
import com.wanted.preonboarding.module.product.dto.CreateProduct;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.user.entity.Users;

import java.util.ArrayList;
import java.util.List;

public class ProductFactory {

    public static Product generateProduct(CreateProduct createProduct) {
        Users usersWithId = UsersModuleHelper.toUsersWithId();
        Product product = ProductModuleHelper.toProductWithId(createProduct);
        product.setSeller(usersWithId);
        return product;
    }

    public static List<Sku> generateSkus(int size) {
        List<BaseSku> baseSkus = generateBaseSkus(size);
        return new ArrayList<>(baseSkus);
    }

    public static List<BaseSku> generateBaseSkus(int size) {
        List<BaseSku> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            data.add(generateBaseSku());
        }

        return data;
    }


    public static Sku generateSku() {
        CreateProduct createProduct = ProductModuleHelper.toCreateProductWithUsers();
        Product product = generateProduct(createProduct);
        return ProductModuleHelper.toSku(product);
    }

    public static BaseSku generateBaseSku() {
        CreateProduct createProduct = ProductModuleHelper.toCreateProductWithUsers();
        Product product = generateProduct(createProduct);
        return ProductModuleHelper.toSku(product);
    }

}
