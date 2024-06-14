package io.github.potatoy.wanted_preonboarding_challenge.market.entity;

import io.github.potatoy.wanted_preonboarding_challenge.user.entity.User;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface ProductRepository extends JpaRepository<Product, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<Product> findById(Long id);

  List<Product> findBySellerUserAndBuyerUser(User sellerUser, User buyerUser);

  List<Product> findByBuyerUserAndSellerUser(User buyerUser, User sellerUser);
}
