package wanted.challenge.mypage.dto.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
        private int orderId;
        private int goodsId;
        private String goodsName;
        private String goodsStatus;
        private int buyerId;
        private int buyerName;
        private String orderStatus;
        private int quantity;
        private int orderPrice;
        private Long orderDate;
        private Long confirmDate;
        private Long finishDate;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    public static class buyOrderDetail {
        private int orderId;
        private int goodsId;
        private String goodsName;
        private String goodsStatus;
        private int sellerId;
        private int sellerName;
        private String orderStatus;
        private int quantity;
        private int orderPrice;
        private Long orderDate;
        private Long confirmDate;
        private Long finishDate;
    }
}
