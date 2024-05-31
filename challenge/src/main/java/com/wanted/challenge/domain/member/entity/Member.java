package com.wanted.challenge.domain.member.entity;

import com.wanted.challenge.domain.item.entity.Item;
import com.wanted.challenge.domain.transactionhistory.entity.TransactionHistory;
import com.wanted.challenge.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<TransactionHistory> transactionHistories = new ArrayList<>();

    @Builder
    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
