package com.market.wanted.member;

import lombok.Data;

@Data
public class MemberSignDto {
    private String username;
    private String password;
    private String passwordCheck;
    private String name;
}
