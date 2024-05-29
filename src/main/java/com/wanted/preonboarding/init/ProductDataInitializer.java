package com.wanted.preonboarding.init;

import com.wanted.preonboarding.data.product.ProductFactory;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.product.repository.ProductJdbcRepository;
import com.wanted.preonboarding.module.user.entity.Users;
import com.wanted.preonboarding.module.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Slf4j
@Component
@RequiredArgsConstructor
public class ProductDataInitializer extends AbstractDataInitializerTracker implements DataGenerator  {

    private final ProductJdbcRepository productJdbcRepository;
    private final UserRepository userRepository;
    private static final String PRODUCT_ENTITY= "PRODUCT";
    private final Map<String, Integer> userProductCount = new HashMap<>();

    @Override
    public void generate(int size) {
        super.start(PRODUCT_ENTITY);
        initProduct(size);
        end(PRODUCT_ENTITY);
    }

    @Override
    public void end(String entity) {
        String topUserEmail = Collections.max(userProductCount.entrySet(), Map.Entry.comparingByValue()).getKey();
        log.info("User with most products: {}", topUserEmail);
        super.end(entity);
        userProductCount.clear();
    }

    private void initProduct(int size) {
        List<Users> users = userRepository.findAll();
        Random random = new Random();
        List<Product> products = new ArrayList<>();

        Users masterUser = users.get(0);
        addProductsForUser(masterUser, size, products);

        for (int i = 0; i < size; i++) {
            Users randomUser = users.get(random.nextInt(users.size()));
            addProductsForUser(randomUser, 1, products); // 1개의 제품만 추가
        }

        productJdbcRepository.saveAll(products);
    }

    private void addProductsForUser(Users user, int productCount, List<Product> products) {
        for (int i = 0; i < productCount; i++) {
            products.add(ProductFactory.generateProduct(user));
            String email = user.getEmail();
            userProductCount.put(email, userProductCount.getOrDefault(email, 0) + 1);
        }
    }

}
