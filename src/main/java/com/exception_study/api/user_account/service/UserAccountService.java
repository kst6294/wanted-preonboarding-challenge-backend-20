package com.exception_study.api.user_account.service;

import com.exception_study.api.user_account.dto.UserAccountDto;
import com.exception_study.api.user_account.dto.request.LoginRequest;
import com.exception_study.api.user_account.dto.response.LoginResponse;
import com.exception_study.api.user_account.entity.UserAccount;
import com.exception_study.api.user_account.repository.UserAccountRepository;
import com.exception_study.global.config.security.TokenProvider;
import com.exception_study.global.exception.ErrorCode;
import com.exception_study.global.exception.StudyApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Value("JwtSecretKey")
    private String SECRET_KEY;

    public void signUp(UserAccountDto dto) {
        if (userAccountRepository.existsById(dto.getUserId())) {
            throw new StudyApplicationException(ErrorCode.USER_ALREADY_EXIST, String.format("user id is %s",dto.getUserId()));
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        UserAccount userAccount = UserAccount.of(dto.getUserId(), encodedPassword, dto.getUserName());
        userAccountRepository.save(userAccount);
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        String userId = request.getUserId();
        String password = request.getPassword();
        UserAccount userAccount = userAccountRepository.findById(userId).orElseThrow(
                () -> new StudyApplicationException(ErrorCode.USER_NOT_FOUND, String.format("user id is %s",userId))
        );

        if (!passwordEncoder.matches(password, userAccount.getPassword())) {
            throw new StudyApplicationException(ErrorCode.INVALID_PASSWORD);
        }
        String token = tokenProvider.createAccessToken(userId);
        long expiredTime = 1800000L;

        return LoginResponse.of(token, expiredTime);
    }


}
