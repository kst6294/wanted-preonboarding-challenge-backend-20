package com.backend.market.DAO.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long product_id;

    String product_name;

    double price;

    Status status;

    int quantity;

    LocalDate creaeDate;

    @ManyToOne
    Member member;
}
