package com.wanted.preonboarding.init;


import com.wanted.preonboarding.data.order.OrderModuleHelper;
import com.wanted.preonboarding.data.product.ProductFactory;
import com.wanted.preonboarding.module.order.dto.CreateOrder;
import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.order.repository.OrderRepository;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.user.entity.Users;
import com.wanted.preonboarding.module.user.repository.UserFindRepositoryImpl;
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
public class OrderDataInitializer extends AbstractDataInitializerTracker implements DataGenerator {

    private final UserFindRepositoryImpl userRepository;
    private final OrderRepository orderRepository;
    private static final String ORDERS_ENTITY= "ORDERS";
    private Integer generateOrderCount = 0;


    @Override
    public void generate(int size) {
        super.start(ORDERS_ENTITY);
        initOrder(size);
        end(ORDERS_ENTITY);
    }

    @Override
    public void end(String entity) {
        log.info("Order generate count: {}", generateOrderCount);
        super.end(entity);
    }

    private void initOrder(int size) {
        List<Users> users = userRepository.fetchAllUsersWithProduct();
        Random random = new Random();
        List<Order> orders = new ArrayList<>();
        Map<Users, Set<Product>> buyerPurchasedProducts = new HashMap<>();
        Users masterUser = users.get(0);

        for (int i = 0; i < size; i++) {
            createOrderForRandomUser(users, random, orders, buyerPurchasedProducts);
        }

        for (int i = 0; i < size; i++) {
            createOrderForMasterUser(users, random, orders, buyerPurchasedProducts, masterUser);
        }

        for (int i = 0; i < size; i++) {
            createOrderForOtherUser(users, random, orders, buyerPurchasedProducts, masterUser);
        }

        orderRepository.saveAll(orders);
        generateOrderCount = orders.size();
    }

    private void createOrderForRandomUser(List<Users> users, Random random, List<Order> orders, Map<Users, Set<Product>> buyerPurchasedProducts) {
        Users buyer = users.get(random.nextInt(users.size()));
        Users seller;

        do {
            seller = users.get(random.nextInt(users.size()));
        } while (buyer.equals(seller));

        createOrder(buyer, seller, orders, buyerPurchasedProducts);
    }

    private void createOrderForMasterUser(List<Users> users, Random random, List<Order> orders, Map<Users, Set<Product>> buyerPurchasedProducts, Users masterUser) {
        Users seller;

        do {
            seller = users.get(random.nextInt(users.size()));
        } while (masterUser.equals(seller));

        createOrder(masterUser, seller, orders, buyerPurchasedProducts);
    }

    private void createOrderForOtherUser(List<Users> users, Random random, List<Order> orders, Map<Users, Set<Product>> buyerPurchasedProducts, Users masterUser) {
        Users buyer = users.get(random.nextInt(users.size()));

        do {
            masterUser = users.get(random.nextInt(users.size()));
        } while (buyer.equals(masterUser));

        createOrder(buyer, masterUser, orders, buyerPurchasedProducts);
    }

    private void createOrder(Users buyer, Users seller, List<Order> orders, Map<Users, Set<Product>> buyerPurchasedProducts) {
        Set<Product> products = seller.getProducts();

        Optional<Product> first = products.stream().findFirst();
        if (first.isEmpty()) return;

        Product product = first.get();

        Set<Product> purchasedProducts = buyerPurchasedProducts.getOrDefault(buyer, new HashSet<>());
        if (purchasedProducts.contains(product)) return;

        if (product.getQuantity() > 0) {
            product.doBooking();
            orders.add(OrderModuleHelper.toOrder(product, buyer));

            purchasedProducts.add(product);
            buyerPurchasedProducts.put(buyer, purchasedProducts);
        }
    }

}
