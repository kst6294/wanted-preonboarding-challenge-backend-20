package com.backend.market.Request;

import com.backend.market.DAO.Entity.Member;
import com.backend.market.DAO.Entity.Status;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ProductReq {
    Long product_id;

    String product_name;

    double price;

    Status status;

    int quantity;

    LocalDate creaeDate;

    Member member;
}
