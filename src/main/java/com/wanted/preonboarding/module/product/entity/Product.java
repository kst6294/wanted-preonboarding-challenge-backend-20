package com.wanted.preonboarding.module.product.entity;

import com.wanted.preonboarding.module.common.entity.BaseEntity;
import com.wanted.preonboarding.module.product.enums.ProductStatus;
import com.wanted.preonboarding.module.user.entity.Users;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private Users seller;

    public void setSeller(Users seller) {
        if (this.seller != null) {
            this.seller.getProducts().remove(this);
        }
        this.seller = seller;
        if (seller != null) {
            seller.getProducts().add(this);
        }
    }

    public void doBooking(){
        productStatus = ProductStatus.BOOKING;
    }


}
