package wanted.market.api.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wanted.market.api.domain.user.entity.User;
import wanted.market.api.domain.user.repository.UserRepository;
import wanted.market.api.global.response.enums.ExceptionDomain;
import wanted.market.api.global.response.enums.ExceptionMessage;
import wanted.market.api.global.response.exception.WantedException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User getUser(Long userId){
        return userRepository.findById(userId).orElseThrow(()->new WantedException(ExceptionDomain.USER,ExceptionMessage.ISNOTUSER));
    }
}
