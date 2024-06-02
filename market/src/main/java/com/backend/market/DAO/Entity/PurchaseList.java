package com.backend.market.DAO.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
public class PurchaseList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long buyerId;

    Long sellerId;

    Long product_id;

    OrderStatus orderStatus;

    LocalDate creaeDate;
}
