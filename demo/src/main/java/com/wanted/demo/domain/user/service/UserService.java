package com.wanted.demo.domain.user.service;

import com.wanted.demo.domain.exception.exception.UserException;
import com.wanted.demo.domain.exception.responseCode.UserExceptionResponseCode;
import com.wanted.demo.domain.user.dto.request.LoginRequestDTO;
import com.wanted.demo.domain.user.dto.request.SignUpRequestDTO;
import com.wanted.demo.domain.user.entity.User;
import com.wanted.demo.domain.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Transactional
    public void signUp(SignUpRequestDTO signUpRequestDTO){

        //이메일 중복 확인
        if(userRepository.existsByEmail(signUpRequestDTO.getEmail()))
            throw new UserException(UserExceptionResponseCode.EXISTS_USER,"이미 존재하는 멤버입니다.");

        //저장
        userRepository.save(User.builder()
                .email(signUpRequestDTO.getEmail())
                .password(passwordEncoder.encode(signUpRequestDTO.getPassword())).build());
    }

    //로그인
    @Transactional
    public void login(LoginRequestDTO loginRequestDTO, HttpServletRequest request){
        //이메일 없음
        User user = userRepository.findByUser(loginRequestDTO.getEmail())
                .orElseThrow(() -> new UserException(UserExceptionResponseCode.NOT_EXISTS_USER, loginRequestDTO.getEmail()+"로그인 요청(없는 이메일)"));

        //비밀번호 틀림
        if(!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())){
            throw new UserException(UserExceptionResponseCode.NOT_MATCH_PASSWORD, loginRequestDTO.getPassword()+"로그인 요청(틀린 비밀번호)");
        }

        //로그인 로직
        addSessionAndCookie(request,user);
    }


    // 세션 생성 및 쿠키에 저장
    private void addSessionAndCookie(HttpServletRequest request,  User user){
        HttpSession session = request.getSession();
        session.setAttribute("userId", user.getId());
        session.setMaxInactiveInterval(1800); // 30분

        String sessionId = session.getId();
        Cookie sessionCookie = new Cookie("JSESSIONID", sessionId);
        sessionCookie.setHttpOnly(true);
    }



}
