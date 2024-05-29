package com.wanted.market.global.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.market.global.common.code.ResponseCode;
import com.wanted.market.global.common.response.BaseResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("CustomAccessDeniedHandler.handle");

        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse res = new BaseResponse(ResponseCode.NO_PERMISSION);

        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("utf-8");

        String responseValue = objectMapper.writeValueAsString(res);
        response.getWriter().write(responseValue);
    }
}
