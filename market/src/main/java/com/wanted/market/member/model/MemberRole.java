package com.wanted.market.member.model;

public enum MemberRole {
    ROLE_BUYER("buyer"),
    ROLE_SELLER("seller");

    private final String status;


    MemberRole(String status) {
        this.status = status;
    }

}
