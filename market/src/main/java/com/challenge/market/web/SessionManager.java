package com.challenge.market.web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {
        private final String SESSION_COOKIE_NAME = "mySessionCookie";
        // 동시성 이슈에 대비해 concurrentHashMap으로 생성한다.
        private final Map<String,Object> sessionStore = new ConcurrentHashMap<>();
        public void createSession(Object member, HttpServletResponse response) {
            // 세션 id 를 생성.
            String sessionId = UUID.randomUUID().toString();

            // 세션 공간에 member 객체 저장.
            sessionStore.put(sessionId,member);

            // 응답에 cookie를 담아 보낸다.
            response.addCookie(new Cookie(SESSION_COOKIE_NAME, sessionId ));
        }
        public Object getSession(HttpServletRequest request){
            Cookie sessionCookie = findCookie(request);
            if(sessionCookie == null){
                return null;
            }
            return sessionStore.get(sessionCookie.getValue());
        }
        private Cookie findCookie(HttpServletRequest request) {
            return  request.getCookies() == null?  null :
            Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(SESSION_COOKIE_NAME))
                    .findAny()
                    .orElse(null);
        }
        public void expire(HttpServletRequest request){
            Cookie sessionCookie = findCookie(request);
            if(sessionCookie != null){
                sessionStore.remove(sessionCookie.getValue());
            }
        }
    }