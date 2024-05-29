package com.wanted.market.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wanted.market.domain.trade.entity.Trade;
import com.wanted.market.global.common.code.BaseStatusCode;
import com.wanted.market.global.common.code.RoleCode;
import com.wanted.market.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "member")
@Getter
@Entity
@Builder
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "member_number")
    private long memberNo;

    @Column(name = "member_id")
    private String memberId;

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private RoleCode role;

    @Enumerated(EnumType.STRING)
    private BaseStatusCode status;

    @JsonIgnore
    @OneToMany(mappedBy = "seller")
    private List<Trade> sellerTrades = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "buyer")
    private List<Trade> buyerTrades = new ArrayList<>();

    public Member() {
    }

    public Member(Long memberNo, String memberId, String name, String password, RoleCode role,
                  BaseStatusCode status, List<Trade> sellerTrades, List<Trade> buyerTrades) {
        this.memberNo = memberNo;
        this.memberId = memberId;
        this.name = name;
        this.password = password;
        this.role = role;
        this.status = status;
        this.sellerTrades = sellerTrades;
        this.buyerTrades = buyerTrades;
    }
}
