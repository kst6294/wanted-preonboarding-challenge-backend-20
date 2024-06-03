package io.github.potatoy.wanted_preonboarding_challenge.market.entity;

import io.github.potatoy.wanted_preonboarding_challenge.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

  List<Product> findBySellerUserAndBuyerUser(User sellerUser, User buyerUser);

  List<Product> findByBuyerUserAndSellerUser(User buyerUser, User sellerUser);
}
