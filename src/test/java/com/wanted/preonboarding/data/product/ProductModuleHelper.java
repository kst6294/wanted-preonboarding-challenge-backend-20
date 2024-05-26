package com.wanted.preonboarding.data.product;

import com.wanted.preonboarding.data.EasyRandomUtils;
import com.wanted.preonboarding.data.FourDigitRandomizer;
import com.wanted.preonboarding.data.users.UsersModuleHelper;
import com.wanted.preonboarding.module.product.core.BaseSku;
import com.wanted.preonboarding.module.product.dto.CreateProduct;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.product.enums.ProductStatus;
import org.jeasy.random.EasyRandom;

import java.util.HashMap;
import java.util.Map;

public class ProductModuleHelper {

    public static CreateProduct toCreateProduct(){
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("users", null);
        EasyRandom instance = EasyRandomUtils.getInstance(stringObjectMap);
        return instance.nextObject(CreateProduct.class);
    }


    public static CreateProduct toCreateProductWithUsers(){
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("users", UsersModuleHelper.toUsersWithId());
        EasyRandom instance = EasyRandomUtils.getInstance(stringObjectMap);
        return instance.nextObject(CreateProduct.class);
    }



    public static Product toProduct(){
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("productStatus", ProductStatus.ON_STOCK);
        EasyRandom instance = EasyRandomUtils.getInstance(stringObjectMap);
        return instance.nextObject(Product.class);
    }


    public static Product toProductWithId(CreateProduct createProduct){
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("id", new FourDigitRandomizer().getRandomValue());
        stringObjectMap.put("productName", createProduct.getProductName());
        stringObjectMap.put("price", createProduct.getPrice());
        stringObjectMap.put("productStatus", ProductStatus.ON_STOCK);
        EasyRandom instance = EasyRandomUtils.getInstance(stringObjectMap);
        return instance.nextObject(Product.class);
    }




    public static BaseSku toSku(Product product){
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("id", product.getId());
        stringObjectMap.put("productName", product.getProductName());
        stringObjectMap.put("price", product.getPrice());
        stringObjectMap.put("productStatus", product.getProductStatus());
        stringObjectMap.put("seller", product.getSeller().getEmail());

        EasyRandom instance = EasyRandomUtils.getInstance(stringObjectMap);
        return instance.nextObject(BaseSku.class);
    }


}
