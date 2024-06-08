package market.market.domain.user.service;

import market.market.domain.user.domain.User;
import market.market.domain.user.facade.UserFacade;
import market.market.domain.user.presentation.dto.request.LoginRequest;
import market.market.global.error.ErrorCode;
import market.market.global.error.exeception.CustomException;
import market.market.global.security.jwt.dto.TokenResponse;
import market.market.global.security.jwt.repository.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserFacade userFacade;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public TokenResponse execute(LoginRequest request){
        User user = userFacade.getUserByAccountId(request.getAccountId());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new CustomException(ErrorCode.PASSWORD_MISS_MATCHED);
        }

        return jwtProvider.getToken(user);
    }
}
