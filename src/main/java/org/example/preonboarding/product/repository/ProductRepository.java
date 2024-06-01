package org.example.preonboarding.product.repository;

import org.example.preonboarding.member.model.entity.Member;
import org.example.preonboarding.product.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p.productNumber FROM Product p")
    List<String> findAllProductNumbers();

    Product findByProductNumber(String productNumber);

    List<Product> findAllBySeller(Member member);

    List<Product> findAllByProductNumberIn(List<String> productNumbers);

}
