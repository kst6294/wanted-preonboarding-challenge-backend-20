package com.wanted.market.global.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.market.global.common.code.ResponseCode;
import com.wanted.market.global.common.response.BaseResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class LoginAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("LoginAuthenticationFailureHandler.onAuthenticationFailure");

        ObjectMapper objectMapper = new ObjectMapper();

        String responseValue = objectMapper.writeValueAsString(new BaseResponse(ResponseCode.BAD_REQUEST));

        response.setContentType("application/json;");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(responseValue);
    }
}
