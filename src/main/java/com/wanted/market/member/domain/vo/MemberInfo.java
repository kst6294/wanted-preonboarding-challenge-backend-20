package com.wanted.market.member.domain.vo;

public class MemberInfo {
    private final Long id;
    private final String username;

    public MemberInfo(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
