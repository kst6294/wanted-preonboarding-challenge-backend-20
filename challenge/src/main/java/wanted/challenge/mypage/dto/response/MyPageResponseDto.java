package wanted.challenge.mypage.dto.response;


import wanted.challenge.goods.dto.response.GoodsResponseDto;

import java.time.LocalDateTime;

import static wanted.challenge.goods.dto.response.GoodsResponseDto.*;

public class MyPageResponseDto {

    public enum OrderStatus {
        승인대기, 판매승인, 구매확정;

        public static OrderStatus of(String orderStatus) {
            try {
                return OrderStatus.valueOf(orderStatus);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("잘못된 orderStatus: " + orderStatus, e);
            }
        }
    }

    public record Trade(
            Long orderId,
            LocalDateTime tradeAt,
            OrderStatus tradeStatus,
            int tradePrice,
            int quantity
    ) {
        //확장성을 위해 생성자 추가
        public Trade(Long orderId, LocalDateTime tradeAt, String tradeStatus, int tradePrice, int quantity) {
            this(orderId, tradeAt, OrderStatus.of(tradeStatus), tradePrice, quantity);
        }
    }

    public record orderListItemInfo(
            Long orderId,
            String goodsName,
            OrderStatus orderStatus,
            int quantity
    ) {
    }

    public record sellOrderDetail(
            Long orderId,
            Long goodsId,
            String goodsName,
            GoodsStatus goodsStatus,
            Long buyerId,
            String buyerName,
            OrderStatus orderStatus,
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
            GoodsStatus goodsStatus,
            Long sellerId,
            String sellerName,
            OrderStatus orderStatus,
            int quantity,
            int orderPrice,
            LocalDateTime orderDate,
            LocalDateTime confirmDate,
            LocalDateTime finishDate
    ) {
    }
}
