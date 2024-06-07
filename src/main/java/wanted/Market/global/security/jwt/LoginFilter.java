package wanted.Market.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;
import wanted.Market.domain.member.dto.MemberLoginRequest;
import wanted.Market.global.exception.AppException;
import wanted.Market.global.exception.ErrorCode.AuthErrorCode;

import java.io.IOException;


@RequiredArgsConstructor
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            MemberLoginRequest memberLoginRequest = objectMapper.readValue(request.getReader(), MemberLoginRequest.class);

            if (!isValidJsonAuthenticationRequest(memberLoginRequest)) {
                throw new AppException(AuthErrorCode.INVALID_JSON_FORMAT);
            }
            log.info("username= {}, password = {}", memberLoginRequest.getUsername(), memberLoginRequest.getPassword());

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                memberLoginRequest.getUsername(),
                memberLoginRequest.getPassword()
            );

            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            // JSON 파싱 오류 처리
            throw new AppException(AuthErrorCode.INVALID_JSON_FORMAT);
        } catch (AuthenticationException e) {
            // 인증 오류 처리
            throw new AppException(AuthErrorCode.AUTHENTICATION_FAILED);
        }
    }

    private boolean isValidJsonAuthenticationRequest(MemberLoginRequest memberLoginRequest) {
        return memberLoginRequest != null && StringUtils.hasText(memberLoginRequest.getUsername()) && StringUtils.hasText(memberLoginRequest.getPassword());
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        String userName = authentication.getName();
        //토큰 생성
        String AccessToken = jwtTokenProvider.createAccessToken(userName);

        //응답 설정
        response.setHeader("access", AccessToken);
        response.setStatus(HttpStatus.OK.value());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed){
        response.setStatus(401);
    }
}