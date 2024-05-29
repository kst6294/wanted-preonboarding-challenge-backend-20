package com.wanted.market.global.auth.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.market.global.auth.handler.LoginAuthenticationFailureHandler;
import com.wanted.market.global.auth.handler.LoginAuthenticationSuccessHandler;
import com.wanted.market.global.auth.provider.CustomAuthenticationProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String LOGIN_URL = "/login";
    private static final String HTTP_METHOD = "POST";
    private static final String CONTENT_TYPE = "application/json";

    public LoginAuthenticationFilter(CustomAuthenticationProvider authenticationProvider, LoginAuthenticationSuccessHandler successHandler, LoginAuthenticationFailureHandler failureHandler ) {
        super(new AntPathRequestMatcher(LOGIN_URL, HTTP_METHOD));
        setAuthenticationManager(new ProviderManager(authenticationProvider));
        setAuthenticationSuccessHandler(successHandler);
        setAuthenticationFailureHandler(failureHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        log.info("LoginAuthenticationFilter.attemptAuthentication");

        if (request.getContextPath() == null || !CONTENT_TYPE.equals(request.getContentType())) {
            throw new AuthenticationServiceException("Authentication content type not supported");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8));

        String id = jsonNode.get("id").asText();
        String password = jsonNode.get("password").asText();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, password);
        return getAuthenticationManager().authenticate(authenticationToken);
    }
}
