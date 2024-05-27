package com.backend.market.DAO.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Member {

    //기본키, 자동으로 1씩증가시키기위함
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;

    String name;

    String password;

    //제품과 연결, 회원이 사라지면 해당 제품도 다같이 삭제
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    List<Product> productList;

}
