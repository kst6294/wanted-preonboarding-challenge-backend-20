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


    @Getter
    @Setter
    @NoArgsConstructor
    public static class orderListItemInfo {
        private Long orderId;
        private String goodsName;
        private String orderStatus;
        private int quantity;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class sellOrderDetail {
        private Long orderId;
        private Long goodsId;
        private String goodsName;
        private String goodsStatus;

        private Long buyerId;
        private String buyerName;

        private String orderStatus;
        private int quantity;
        private int orderPrice;
        private LocalDateTime orderDate;
        private LocalDateTime confirmDate;
        private LocalDateTime finishDate;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    public static class buyOrderDetail {
        private Long orderId;
        private Long goodsId;
        private String goodsName;
        private String goodsStatus;

        private Long sellerId;
        private String sellerName;

        private String orderStatus;
        private int quantity;
        private int orderPrice;
        private LocalDateTime orderDate;
        private LocalDateTime confirmDate;
        private LocalDateTime finishDate;
    }
}
