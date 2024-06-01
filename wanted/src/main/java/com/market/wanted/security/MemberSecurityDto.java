package com.market.wanted.security;

import lombok.Builder;
import lombok.Data;

@Data
public class MemberSecurityDto {
    private Long id;
    private String username;
    private String password;
    private String name;

    @Builder
    private MemberSecurityDto(Long id, String username, String password, String name) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
    }
}
