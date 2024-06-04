package com.wanted.market.global.web.argumentresolver;

import com.wanted.market.common.authentication.model.SessionLoginMember;
import com.wanted.market.common.authentication.model.annotation.LoginMember;
import com.wanted.market.common.exception.UnauthorizedRequestException;
import com.wanted.market.global.web.constance.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

public class SessionLoginMemberArgumentResolver extends LoginMemberArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class)
                && SessionLoginMember.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);
        LoginMember parameterAnnotation = parameter.getParameterAnnotation(LoginMember.class);
        if (parameterAnnotation.required() && session == null) {
            throw new UnauthorizedRequestException("Required session is null");
        }
        return session == null ? SessionLoginMember.unauthenticated() : session.getAttribute(SessionConst.SESSION_KEY);
    }
}
