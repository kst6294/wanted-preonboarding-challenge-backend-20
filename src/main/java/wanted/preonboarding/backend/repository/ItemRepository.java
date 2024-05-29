package wanted.preonboarding.backend.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import wanted.preonboarding.backend.domain.entity.Item;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @EntityGraph(attributePaths = {"member"})
    Optional<Item> findItemById(Long id);
}
