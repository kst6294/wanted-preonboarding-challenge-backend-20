package com.wanted.market.common.authentication.model;

public abstract class LoginMember {
    private final Long id;
    private final String username;
    private final boolean isAuthenticated;

    protected LoginMember(boolean isAuthenticated) {
        this.id = null;
        this.username = null;
        this.isAuthenticated = isAuthenticated;
    }

    protected LoginMember(Long id, String username) {
        this.id = id;
        this.username = username;
        this.isAuthenticated = true;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAuthenticated() {
        return this.isAuthenticated;
    }
}
