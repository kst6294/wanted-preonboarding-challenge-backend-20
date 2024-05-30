package wanted.challenge.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wanted.challenge.goods.entity.Goods;
@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {
}
