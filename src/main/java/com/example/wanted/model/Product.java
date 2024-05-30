package com.example.wanted.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long p_id;
    private String name;
    private String description;
    private int price;
    @Enumerated(EnumType.STRING)
    private State state;
    private String seller;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("product")
    private List<Transaction> transactions;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "u_id")
    private User user;

    @Builder
    public Product(Long p_id, String name, String description, int price, State state, String seller, List<Transaction> transactions, User user) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.state = state;
        this.seller = seller;
        this.transactions = transactions;
        this.user = user;
    }
}
