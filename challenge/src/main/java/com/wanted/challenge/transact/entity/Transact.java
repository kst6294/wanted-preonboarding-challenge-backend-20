package com.wanted.challenge.transact.entity;

import com.wanted.challenge.account.entity.Account;
import com.wanted.challenge.product.entity.Product;
import com.wanted.challenge.transact.model.TransactState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "transacts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transact {

    @Id
    @Column(name = "transact_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", referencedColumnName = "account_id")
    Account buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    Product product;

    @Enumerated(EnumType.STRING)
    @Column(name = "transact_state_code")
    TransactState transactState;

    public Transact(Account buyer, Product product, TransactState transactState) {
        this.buyer = buyer;
        this.product = product;
        this.transactState = transactState;
    }
}
