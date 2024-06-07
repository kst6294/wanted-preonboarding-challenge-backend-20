package com.want.BEProject.utils;

import jakarta.servlet.http.HttpSession;

public class SessionUtil {

    private static final String LOGIN_ID = "LOGIN_ID";

    private SessionUtil() {
    }

    public static String getLoginId(HttpSession session) {
        return (String) session.getAttribute(LOGIN_ID);
    }

    public static void setLoginId(HttpSession session, String loginId) {
        session.setAttribute(LOGIN_ID, loginId);
    }

    public static void clear(HttpSession session) {
        session.invalidate();
    }

}
