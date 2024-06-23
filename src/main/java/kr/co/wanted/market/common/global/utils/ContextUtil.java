package kr.co.wanted.market.common.global.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContextUtil {


    public static Optional<Long> getMemberId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        try {
            return Optional.of(Long.valueOf(authentication.getName()));
        } catch (Exception e) {
            return Optional.empty();
        }

    }

}
