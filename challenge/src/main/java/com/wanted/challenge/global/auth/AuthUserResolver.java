package com.wanted.challenge.global.auth;

import com.wanted.challenge.domain.exception.exception.MemberException;
import com.wanted.challenge.domain.exception.info.MemberExceptionInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthUserResolver implements HandlerMethodArgumentResolver {

    private final static String USER_ID = "userId";

    // 주어진 메서드 매개변수가 리졸버에 지원되는지 판단
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthUser.class);
    }

    // 실제 검증 부분
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final Long userId = (Long) webRequest.getAttribute(USER_ID, WebRequest.SCOPE_SESSION);

        if (userId == null){
            throw new MemberException(MemberExceptionInfo.NOT_FOUND_SESSION, "세션이 만료되었거나 유효하지 않습니다.");
        }

        return userId;
    }
}
