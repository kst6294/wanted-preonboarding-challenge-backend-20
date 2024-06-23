package kr.co.wanted.market.common.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.wanted.market.common.global.utils.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final static String TOKEN_HEADER_NAME = "Authorization";
    private final static String TOKEN_HEADER_VALUE_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String tokenHeader = request.getHeader(TOKEN_HEADER_NAME);

        if (StringUtils.hasText(tokenHeader) && tokenHeader.startsWith(TOKEN_HEADER_VALUE_PREFIX)) {

            final String accessToken = tokenHeader.replace(TOKEN_HEADER_VALUE_PREFIX, "");

            final DecodedJWT decodedJWT = tokenProvider.parse(accessToken);
            final Authentication authentication = tokenProvider.getAuthentication(decodedJWT);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Security Context 사용자 [{}] 인증, 요청 uri: [{}]", authentication.getName(), request.getRequestURI());
        }

        filterChain.doFilter(request, response);
    }

}
