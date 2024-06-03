package wanted.challenge.mypage.dto.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class MyPageResponseDto {

    // 판매한 제품 리스트
    // 판매중인 제품 상세조회

    // 구매한 제품 리스트
    // 구매중인 제품 상세조회21


    public record orderListItemInfo(
            Long orderId,
            String goodsName,
            String orderStatus,
            int quantity
    ) {
    }

    public record sellOrderDetail(
            Long orderId,
            Long goodsId,
            String goodsName,
            String goodsStatus,
            Long buyerId,
            String buyerName,
            String orderStatus,
            int quantity,
            int orderPrice,
            LocalDateTime orderDate,
            LocalDateTime confirmDate,
            LocalDateTime finishDate
    ) {
    }

    public record buyOrderDetail(
            Long orderId,
            Long goodsId,
            String goodsName,
            String goodsStatus,
            Long sellerId,
            String sellerName,
            String orderStatus,
            int quantity,
            int orderPrice,
            LocalDateTime orderDate,
            LocalDateTime confirmDate,
            LocalDateTime finishDate
    ) {
    }
}
