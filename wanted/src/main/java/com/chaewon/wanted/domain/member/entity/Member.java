package com.chaewon.wanted.domain.member.entity;

import com.chaewon.wanted.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Member extends BaseEntity {

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", length = 20, nullable = false)
    private String password;
}
