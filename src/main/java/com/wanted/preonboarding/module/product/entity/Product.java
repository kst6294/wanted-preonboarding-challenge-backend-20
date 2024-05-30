package com.wanted.preonboarding.module.product.entity;

import com.wanted.preonboarding.module.common.entity.BaseEntity;
import com.wanted.preonboarding.module.exception.product.ProductOutOfStockException;
import com.wanted.preonboarding.module.order.entity.OrderProductSnapShot;
import com.wanted.preonboarding.module.product.enums.ProductStatus;
import com.wanted.preonboarding.module.user.entity.Users;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.HashSet;
import java.util.Set;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Table(name = "PRODUCT")
@Entity
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private long id;

    @Column(name = "PRODUCT_NAME", nullable = false)
    private String productName;

    @Column(name = "PRICE", nullable = false)
    private long price;

    @Column(name = "PRODUCT_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @Column(name = "QUANTITY", nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private Users seller;

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderProductSnapShot> orderProductSnapShots = new HashSet<>();


    public void setSeller(Users seller) {
        if (this.seller != null) {
            this.seller.getProducts().remove(this);
        }
        this.seller = seller;
        if (seller != null) {
            seller.getProducts().add(this);
        }
    }

    public void doBooking() {
        if (quantity > 0) {
            quantity--;
            if (quantity == 0) {
                productStatus = ProductStatus.BOOKING;
            }
        }else{
            throw new ProductOutOfStockException(id);
        }

    }

    public void outOfStock(){
        productStatus = ProductStatus.OUT_OF_STOCK;
    }

    public void update(String productName, long price, int quantity){
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

}
