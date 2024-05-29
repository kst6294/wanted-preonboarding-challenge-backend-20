package com.wanted.market.global.auth.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.market.global.common.code.ResponseCode;
import com.wanted.market.global.common.response.BaseResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse res = new BaseResponse(ResponseCode.NO_AUTHORIZATION);

        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("utf-8");

        String responseValue = objectMapper.writeValueAsString(res);
        response.getWriter().write(responseValue);
    }
}
