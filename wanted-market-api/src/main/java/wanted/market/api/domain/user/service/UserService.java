package wanted.market.api.domain.user.service;

import jakarta.servlet.http.HttpServletRequest;
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


    public User getUser(HttpServletRequest request){
        Long userId= Long.parseLong(request.getHeader("userId"));
        return userRepository.findById(userId).orElseThrow(()->new WantedException(ExceptionDomain.USER,ExceptionMessage.IS_NOT_USER));
    }
}
