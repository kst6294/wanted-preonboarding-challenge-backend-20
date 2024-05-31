package com.wanted.market.global.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class SessionUtils {

    public long getMemberNo() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) (RequestContextHolder.getRequestAttributes());
        HttpServletRequest httpRequest = requestAttributes.getRequest();
        HttpSession session = httpRequest.getSession();

        return (long) session.getAttribute("memberNo");
    }
}
