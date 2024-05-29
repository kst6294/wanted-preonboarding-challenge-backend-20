package wanted.challenge.goods.dto.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class GoodsResponseDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class GoodsListResponse {
        private int goodsId;
        private String goodsName;
        private int goodsPrice;
        private String reservedStatus;
        private int quantity;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    public static class GoodsDetailResponse {

        private int goodsName;
        private int goodsPrice;
        private String reservedStatus;
        private int quantity;

        private Long createdAt;
        private List<Trade> tradeList;

        static class Trade {
            private int orderId;
            //거래일자
            private Long tradeAt;
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
    public static class GoodsOrderResponse {
        private int orderId;
    }
}
