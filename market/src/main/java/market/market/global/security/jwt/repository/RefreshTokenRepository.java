package market.market.global.security.jwt.repository;

import market.market.global.security.jwt.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    boolean existsByAccountId(String accountId);
}
