package wanted.market.api.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.market.api.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
