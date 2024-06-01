package com.wanted.market.global.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionUtils {

    private final HttpServletRequest request;

    public long getMemberNo() {
        HttpSession session = request.getSession();
        return (long) session.getAttribute("memberNo");
    }
}
