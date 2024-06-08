package market.market.domain.user.domain.repository;

import market.market.domain.user.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByAccountId(String accountId);

    Boolean existsByAccountId(String accountId);

    boolean existsByEmail(String email);
}
