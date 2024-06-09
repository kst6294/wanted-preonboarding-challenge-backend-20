package wanted.market.domain.product.service.dto.request;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import wanted.market.domain.member.repository.entity.Member;
import wanted.market.domain.product.repository.entity.Product;
import wanted.market.domain.product.repository.entity.ReservationStatus;

@Getter
public class ProductRegisterServiceRequest {

    private String name;

    private String email;

    private int price;

    private ReservationStatus reservationStatus;

    private int quantity;

    private String content;

    @Builder
    private ProductRegisterServiceRequest(String name, String email, int price, ReservationStatus reservationStatus, int quantity, String content) {
        this.name = name;
        this.email = email;
        this.price = price;
        this.reservationStatus = reservationStatus;
        this.quantity = quantity;
        this.content = content;
    }

    public Product toProduct(Member member) {
        return Product.builder()
                .name(name)
                .member(member)
                .price(price)
                .reservationStatus(reservationStatus)
                .remainQuantity(quantity)
                .completeQuantity(0)
                .content(content)
                .build();
    }
}
