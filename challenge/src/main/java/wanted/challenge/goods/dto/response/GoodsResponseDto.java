package wanted.challenge.goods.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class GoodsResponseDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class GoodsList {
        private Long goodsId;
        private String goodsName;
        private int goodsPrice;
        private String reservedStatus;
        private int quantity;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class GoodsDetail {
        private Long goodsId;
        private String goodsName;
        private int goodsPrice;
        private String reservedStatus;
        private int quantity;

        private LocalDateTime createdAt;
        private List<Trade> tradeList;

        @NoArgsConstructor
        @Setter
        @Getter
        public static class Trade {
            private Long orderId;
            //거래일자
            private LocalDateTime tradeAt;
            private String tradeStatus;
            // 구매한 금액
            private int tradePrice;
            //수량
            private int quantity;

        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UpdateGoods {
        private Long goodsId;
        private String goodsName;
        private int goodsPrice;
        private String reservedStatus;
        private int quantity;

        private LocalDateTime createdAt;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class GoodsOrder {
        private int orderId;
    }
}
