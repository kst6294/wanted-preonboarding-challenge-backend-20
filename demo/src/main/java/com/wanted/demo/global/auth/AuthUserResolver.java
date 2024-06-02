package com.wanted.demo.global.auth;

import com.wanted.demo.domain.exception.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthUserResolver  implements HandlerMethodArgumentResolver {
    private final String USER_ID = "userId";

    // 메서드 파라미터가 리졸버 지원되는지 판단
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(OptionalAuthUser.class);
    }

    // 검증
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory){
        final Long userId = (Long) webRequest.getAttribute(USER_ID, WebRequest.SCOPE_SESSION);

//        if(userId == null) throw new UserException()

        return userId;
    }
}
