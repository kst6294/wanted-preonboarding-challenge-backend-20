package com.wanted.market.common.authentication.model;

public class SessionLoginMember extends LoginMember {
    protected SessionLoginMember() {
        super(false);
    }

    protected SessionLoginMember(Long id, String username) {
        super(id, username);
    }

    public static SessionLoginMember authenticated(Long id, String username) {
        return new SessionLoginMember(id, username);
    }

    public static SessionLoginMember unauthenticated() {
        return new SessionLoginMember();
    }
}
