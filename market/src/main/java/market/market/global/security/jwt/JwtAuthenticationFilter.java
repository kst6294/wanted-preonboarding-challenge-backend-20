package market.market.global.security.jwt;

import market.market.global.security.jwt.repository.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // request 에서 token 취함.
        String bearer = jwtProvider.resolveToken(request);

        if(bearer != null){

            // 토큰이 유효하면 토큰으로부터 유저 정보를 가져옴
            Authentication authentication = jwtProvider.authentication(bearer);
            // SpringContext 에 Authentication 객체를 가져옴
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);
    }
}
