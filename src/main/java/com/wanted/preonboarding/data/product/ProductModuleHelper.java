package com.wanted.preonboarding.data.product;

import com.wanted.preonboarding.data.EasyRandomUtils;
import com.wanted.preonboarding.data.FourDigitRandomizer;
import com.wanted.preonboarding.data.users.UsersModuleHelper;
import com.wanted.preonboarding.module.product.core.BaseSku;
import com.wanted.preonboarding.module.product.dto.CreateProduct;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.product.enums.ProductStatus;
import com.wanted.preonboarding.module.user.entity.Users;
import org.jeasy.random.EasyRandom;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ProductModuleHelper {

    public static CreateProduct toCreateProduct(){
        Map<String, Object> stringObjectMap = new HashMap<>();
        Random random = new Random();

        stringObjectMap.put("users", null);
        stringObjectMap.put("quantity", random.nextInt(9000));

        EasyRandom instance = EasyRandomUtils.getInstance(stringObjectMap);
        return instance.nextObject(CreateProduct.class);
    }

    public static CreateProduct toCreateProduct(Users seller){
        Map<String, Object> stringObjectMap = new HashMap<>();
        Random random = new Random();

        stringObjectMap.put("users", seller);
        stringObjectMap.put("quantity", random.nextInt(9000));

        EasyRandom instance = EasyRandomUtils.getInstance(stringObjectMap);
        return instance.nextObject(CreateProduct.class);
    }


    public static CreateProduct toCreateProductWithUsers(){
        Map<String, Object> stringObjectMap = new HashMap<>();
        Random random = new Random();

        stringObjectMap.put("users", UsersModuleHelper.toUsersWithId());
        stringObjectMap.put("quantity", random.nextInt(9000));

        EasyRandom instance = EasyRandomUtils.getInstance(stringObjectMap);
        return instance.nextObject(CreateProduct.class);
    }



    public static Product toProduct(){
        EasyRandom instance = EasyRandomUtils.getInstance();
        return instance.nextObject(Product.class);
    }


    public static Product toProduct(int quantity){
        Map<String, Object> stringObjectMap = new HashMap<>();
        Random random = new Random();

        stringObjectMap.put("id", 0L);
        stringObjectMap.put("productStatus", ProductStatus.ON_STOCK);
        stringObjectMap.put("quantity", quantity);

        EasyRandom instance = EasyRandomUtils.getInstance(stringObjectMap);
        return instance.nextObject(Product.class);
    }




    public static Product toProductWithId(CreateProduct createProduct){
        Map<String, Object> stringObjectMap = new HashMap<>();
        Random random = new Random();

        stringObjectMap.put("id", new FourDigitRandomizer().getRandomValue());
        stringObjectMap.put("productName", createProduct.getProductName());
        stringObjectMap.put("price", createProduct.getPrice());
        stringObjectMap.put("productStatus", ProductStatus.ON_STOCK);
        stringObjectMap.put("quantity", random.nextInt(9000));

        EasyRandom instance = EasyRandomUtils.getInstance(stringObjectMap);
        return instance.nextObject(Product.class);
    }

    public static Product toProductWithId(CreateProduct createProduct, Users seller){
        Map<String, Object> stringObjectMap = new HashMap<>();
        Random random = new Random();

        stringObjectMap.put("id", new FourDigitRandomizer().getRandomValue());
        stringObjectMap.put("productName", createProduct.getProductName());
        stringObjectMap.put("price", createProduct.getPrice());
        stringObjectMap.put("productStatus", ProductStatus.ON_STOCK);
        stringObjectMap.put("quantity", random.nextInt(9000));
        stringObjectMap.put("seller", seller);
        EasyRandom instance = EasyRandomUtils.getInstance(stringObjectMap);
        return instance.nextObject(Product.class);
    }




    public static BaseSku toSku(Product product){
        Map<String, Object> stringObjectMap = new HashMap<>();
        Random random = new Random();
        stringObjectMap.put("id", product.getId());
        stringObjectMap.put("productName", product.getProductName());
        stringObjectMap.put("price", product.getPrice());
        stringObjectMap.put("productStatus", product.getProductStatus());
        stringObjectMap.put("seller", product.getSeller().getEmail());
        stringObjectMap.put("quantity", random.nextInt(9000));

        EasyRandom instance = EasyRandomUtils.getInstance(stringObjectMap);
        return instance.nextObject(BaseSku.class);
    }


}
