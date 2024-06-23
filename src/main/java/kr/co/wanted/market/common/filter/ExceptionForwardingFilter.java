package kr.co.wanted.market.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * 필터에서 발생한 예외를 공통된 GlobalExceptionHandler 에서 처리하도록 하기위한 필터
 */
@Slf4j
@Order(Integer.MIN_VALUE)
@Component
@RequiredArgsConstructor
public class ExceptionForwardingFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {

        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.debug("Filter 에서 Exception 발생");
            handlerExceptionResolver.resolveException(request, response, null, e);
        }

    }
}
