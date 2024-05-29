package com.wanted.market.domain.member.request;

import lombok.Getter;

@Getter
public class JoinRequest {

    private String memberId;

    private String name;

    private String password;
}
