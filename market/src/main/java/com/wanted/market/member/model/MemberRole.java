package com.wanted.market.member.model;

public enum MemberRole {
    BUYER("buyer"),
    SELLER("seller");

    private final String status;


    MemberRole(String status) {
        this.status = status;
    }

}
