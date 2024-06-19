package market.market.domain.user.service;


import market.market.domain.user.domain.User;
import market.market.domain.user.domain.repository.UserRepository;
import market.market.domain.user.facade.UserFacade;
import market.market.domain.user.presentation.dto.request.SignUpRequest;
import market.market.global.error.ErrorCode;
import market.market.global.error.exeception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserFacade userFacade;

    @Transactional
    public void execute(SignUpRequest request) {

        userFacade.exitsByEmail(request.getEmail());
        userFacade.existsByAccountId(request.getAccountId());

        if (!request.getPassword().equals(request.getPasswordValid())) {
            throw new CustomException(ErrorCode.PASSWORD_MISS_MATCHED);
        }

        userRepository.save(User.builder()
                .email(request.getEmail())
                .accountId(request.getAccountId())
                .password(passwordEncoder.encode(request.getPassword()))
                .build());
    }
}
