package com.wanted.demo.global.util.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


public class SessionTimeoutFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //초기화
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(request instanceof HttpServletRequest){
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpSession session = (HttpSession) httpRequest.getSession(false); //세션이 존재할 때만 가져옴
            if(session != null){
                session.setMaxInactiveInterval(1800); //세션 시간 연장
            }
        }

        chain.doFilter(request,response);
    }
    @Override
    public void destroy() {
        //종료 작업
    }


}
