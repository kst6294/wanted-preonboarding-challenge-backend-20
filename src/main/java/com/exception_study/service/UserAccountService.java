package com.exception_study.service;

import com.exception_study.config.security.TokenProvider;
import com.exception_study.dto.UserAccountDto;
import com.exception_study.dto.request.LoginRequest;
import com.exception_study.dto.response.LoginResponse;
import com.exception_study.dto.response.SignUpResponse;
import com.exception_study.entity.UserAccount;
import com.exception_study.exception.ErrorCode;
import com.exception_study.exception.StudyApplicationException;
import com.exception_study.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Value("JwtSecretKey")
    private String SECRET_KEY;

    public SignUpResponse signUp(UserAccountDto dto){
        log.info("input Data {},{}",dto.getUserId(),dto.getPassword());
        if(userAccountRepository.existsById(dto.getUserId())){
            throw new IllegalArgumentException("Already Exists User!");
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        UserAccount userAccount = UserAccount.of(dto.getUserId(), encodedPassword,dto.getUserName());
        userAccountRepository.save(userAccount);
        return SignUpResponse.of("SignUp Success");
    }

    public LoginResponse login(LoginRequest request){
        String userId = request.getUserId();
        String password = request.getPassword();

        UserAccount userAccount = userAccountRepository.findById(userId).orElseThrow(
                () -> new StudyApplicationException(ErrorCode.USER_NOT_FOUND)
        );
        if(!passwordEncoder.matches(password,userAccount.getPassword())){
            throw new StudyApplicationException(ErrorCode.INVALID_PASSWORD);
        }
        String token = tokenProvider.createAccessToken(userId);
        long expiredTime = 1800000L;

        return LoginResponse.of(token,expiredTime);
    }





}
