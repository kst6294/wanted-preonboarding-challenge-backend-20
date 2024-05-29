package com.wanted.market.global.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class SessionUtils {

    public long getMemberNo(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession();
        return (long) session.getAttribute("memberNo");
    }
}
