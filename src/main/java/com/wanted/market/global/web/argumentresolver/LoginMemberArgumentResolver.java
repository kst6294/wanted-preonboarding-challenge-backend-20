package com.wanted.market.global.web.argumentresolver;

import com.wanted.market.common.authentication.model.annotation.LoginMember;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

public abstract class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class)
                && com.wanted.market.common.authentication.model.LoginMember.class.isAssignableFrom(parameter.getParameterType());
    }
}
