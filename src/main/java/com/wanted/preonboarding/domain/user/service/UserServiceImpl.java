package com.wanted.preonboarding.domain.user.service;

import com.wanted.preonboarding.domain.user.dto.request.AddUserRequest;
import com.wanted.preonboarding.domain.user.dto.request.CheckEmailRequest;
import com.wanted.preonboarding.domain.user.dto.request.LoginUserRequest;
import com.wanted.preonboarding.domain.user.entity.CustomUserDetails;
import com.wanted.preonboarding.domain.user.entity.User;
import com.wanted.preonboarding.domain.user.repository.UserRepository;
import com.wanted.preonboarding.global.exception.entity.RestApiException;
import com.wanted.preonboarding.global.exception.errorCode.UserErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public void join(AddUserRequest addUserRequest) {
        // 1. 유저 확인
        if (userRepository.existsByEmail(addUserRequest.getEmail())) {
            throw new RestApiException(UserErrorCode.EMAIL_ALREADY_EXISTS, "이메일이 ["+addUserRequest.getEmail()+"] 인 유저가 이미 존재합니다.");
        }

        // 2. 비밀번호 암호화
        String encryptedPassword = bCryptPasswordEncoder.encode(addUserRequest.getPassword());

        // 3. 저장
        userRepository.save(addUserRequest.toEntity(encryptedPassword));
    }

    @Override
    public void login(HttpServletRequest request, LoginUserRequest loginUserRequest) {
        // 1. 유저 확인
        User user = userRepository.findByEmail(loginUserRequest.getEmail()).orElseThrow(() -> new RestApiException(UserErrorCode.USER_NOT_FOUND, "이메일이 ["+loginUserRequest.getEmail()+"] 인 유저가 존재하지 않습니다."));
        
        // 2. 비밀번호 확인
        if (!bCryptPasswordEncoder.matches(loginUserRequest.getPassword(), user.getPassword())) throw new RestApiException(UserErrorCode.PASSWORD_MISMATCH, "비밀번호가 일치하지 않습니다.");
        
        // 3. 세션에 유저 로그인
        HttpSession session = request.getSession();
        session.setAttribute("user", CustomUserDetails.of(user));
        session.setMaxInactiveInterval(3600); // 1시간 이상 접속하지 않으면 자동 로그아웃
    }

    @Override
    public boolean checkEmail(CheckEmailRequest checkEmailRequest) {
        return !userRepository.existsByEmail(checkEmailRequest.getEmail());
    }
}
