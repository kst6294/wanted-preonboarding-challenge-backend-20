package org.example.preonboarding.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.preonboarding.auth.payload.request.LoginRequest;
import org.example.preonboarding.auth.payload.response.LoginResponse;
import org.example.preonboarding.common.enums.ResultCode;
import org.example.preonboarding.common.payload.Api;
import org.example.preonboarding.config.security.TokenProvider;
import org.example.preonboarding.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final MemberService memberService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;

    public CustomUsernamePasswordAuthenticationFilter(MemberService memberService,
                                                      AuthenticationManager authenticationManager,
                                                      TokenProvider tokenProvider,
                                                      ObjectMapper objectMapper) {
        this.memberService = memberService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        super.setAuthenticationManager(authenticationManager);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            LoginRequest creds = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    creds.getUserId(),
                    creds.getPassword()
            );

            return getAuthenticationManager().authenticate(authentication);
        } catch (IOException e) {
            throw new AuthenticationServiceException("Failed to read request body", e);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Authentication failed", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String userName = (String) authResult.getPrincipal();
        LoginResponse loginResponse = tokenProvider.generateTokenDto(authResult);

        Api<LoginResponse> res = Api.<LoginResponse>builder()
                .resultCode(ResultCode.LOGIN_SUCCESS)
                .data(loginResponse)
                .build();

        objectMapper.writeValue(response.getWriter(), res);
    }
}