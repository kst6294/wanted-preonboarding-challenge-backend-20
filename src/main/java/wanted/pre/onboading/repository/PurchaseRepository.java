package wanted.pre.onboading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.pre.onboading.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
    Purchase findByProductIdAndBuyerId(Integer productId, Integer buyerId);
}
