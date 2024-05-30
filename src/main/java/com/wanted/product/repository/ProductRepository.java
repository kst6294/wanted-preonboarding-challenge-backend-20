package com.wanted.product.repository;

import com.wanted.product.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

//	@Query(value= "select name, price, status" +
//				  "from product")
//	List<Object> list();

}
