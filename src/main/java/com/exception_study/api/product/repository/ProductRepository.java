package com.exception_study.api.product.repository;

import com.exception_study.api.product.entity.Product;
import com.exception_study.api.user_account.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {


    Product findBySeller(UserAccount user);
}
