package wanted.market.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import wanted.market.domain.member.repository.entity.Member;
import wanted.market.domain.product.repository.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByMember(Member member);
}
