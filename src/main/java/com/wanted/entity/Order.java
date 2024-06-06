package com.wanted.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDate orderDate;

    /* 연관관계 메서드 */
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderProduct(OrderProduct orderProduct){
        this.orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
    }

    /* 생성 메서드 */
    public static Order createOrder(Member member, OrderProduct... orderProducts) {
        Order order = new Order(member, OrderStatus.승인대기, LocalDate.now());
        for(OrderProduct orderProduct : orderProducts){
            order.addOrderProduct(orderProduct);
        }
        return order;
    }

    /* 생성자 */
    public Order(Member member, OrderStatus status, LocalDate orderDate) {
        this.setMember(member);
        this.status = status;
        this.orderDate = orderDate;
    }
}
