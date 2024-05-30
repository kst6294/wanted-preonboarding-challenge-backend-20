package com.example.wanted.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long t_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private Long seller_id;

    @Builder
    public Transaction(Long t_id, Product product, User user, Long seller_id) {
        this.t_id = t_id;
        this.product = product;
        this.user = user;
        this.seller_id = seller_id;
    }

}
