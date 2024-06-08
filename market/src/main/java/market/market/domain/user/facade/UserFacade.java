package market.market.domain.user.facade;

import market.market.domain.user.domain.User;
import market.market.domain.user.domain.repository.UserRepository;
import market.market.global.error.ErrorCode;
import market.market.global.error.exeception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserByEmail(email);
    }

    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

    }
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public void existsByAccountId(String accountId){
        if(userRepository.existsByAccountId(accountId)){
            throw new CustomException(ErrorCode.EXIST_USER);
        }
    }

    public User getUserByAccountId(String accountId) {
        return userRepository.findUserByAccountId(accountId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
