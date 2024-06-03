package com.wanted.demo.domain.product.entity;

import com.wanted.demo.domain.baseEntity.BaseEntity;
import com.wanted.demo.domain.statements.entity.Statements;
import com.wanted.demo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Statements> statements = new ArrayList<>();


    @Builder
    public Product(String name, Long price, Long quantity, State state, User user) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.state = state;
        this.user = user;
    }

    public void decreaseQuantity(){
        this.quantity -= 1;
    }

    public void updateStatus(){
        this.state = State.RESERVATION;
    }

}
