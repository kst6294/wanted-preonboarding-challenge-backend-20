package wanted.preonboarding.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.preonboarding.backend.domain.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
