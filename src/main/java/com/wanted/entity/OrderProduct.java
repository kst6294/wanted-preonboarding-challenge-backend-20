package com.wanted.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProduct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Long count;
    private Long price;

    /* 비즈니스 로직 */
    public static OrderProduct createOrderProduct(Product product, Long count, Long price){
        OrderProduct orderProduct = new OrderProduct(product, count, price);

        product.removeStock(count);

        return orderProduct;
    }

    public void cancel(){
        getProduct().addStock(count);
    }

    public Long getTotalPrice(){
        return getCount() * getPrice();
    }

    /* 생성자 */
    public OrderProduct(Product product, Long count, Long price) {
        this.product = product;
        this.count = count;
        this.price = price;
    }

    /* setter */
    public void setOrder(Order order) {
        this.order = order;
    }
}
