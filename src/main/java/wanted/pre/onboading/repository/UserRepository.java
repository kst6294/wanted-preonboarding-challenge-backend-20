package wanted.pre.onboading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.pre.onboading.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
