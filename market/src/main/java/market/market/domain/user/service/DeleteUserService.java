package market.market.domain.user.service;

import market.market.domain.user.domain.User;
import market.market.domain.user.domain.repository.UserRepository;
import market.market.domain.user.facade.UserFacade;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserService {

    private final UserFacade userFacade;
    private final UserRepository userRepository;

    @Transactional
    public void execute() {
        User user = userFacade.getCurrentUser();

        userRepository.delete(user);
    }
}
