package com.example.wanted.config.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserFailHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        System.out.println("fail");
        String errorMsg = null;
        if (exception instanceof BadCredentialsException) {
            errorMsg = "아이디 또는 비밀번호가 일치하지 않습니다eeee";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMsg = "존재하지 않는 사용자 입니다";
        } else if (exception instanceof DisabledException) {
            errorMsg = "비활성화 된 계정입니다";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            errorMsg = "없는 계정입니다.";
        } else if (exception instanceof LockedException) {
            errorMsg = "잠긴 계정입니다";
        } else {
            errorMsg = "fail";
        }
        request.setAttribute("errorMsg", errorMsg);
        request.getRequestDispatcher("/login?error=true").forward(request, response);
    }
}
