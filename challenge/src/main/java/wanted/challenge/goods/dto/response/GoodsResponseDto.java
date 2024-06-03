package wanted.challenge.goods.dto.response;



import wanted.challenge.mypage.dto.response.MyPageResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public class GoodsResponseDto {

    public enum GoodsStatus {
        판매중, 예약중, 판매완료;

        public static GoodsStatus of(String reservedStatus) {
            try {
                return GoodsStatus.valueOf(reservedStatus);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("잘못된 reservedStatus: " + reservedStatus, e);
            }
        }
    }

    public record GoodsList(
            Long goodsId,
            String goodsName,
            int goodsPrice,
            GoodsStatus reservedStatus,
            int quantity
    ) {
    }

    public record GoodsDetail(
            Long goodsId,
            String goodsName,
            int goodsPrice,
            GoodsStatus reservedStatus,
            int quantity,
            LocalDateTime createdAt,
            List<MyPageResponseDto.Trade> tradeList
    ) {

    }



    public record UpdateGoods(
            Long goodsId,
            String goodsName,
            int goodsPrice,
            GoodsStatus reservedStatus,
            int quantity,
            LocalDateTime createdAt
    ) {
    }

    public record GoodsOrder(
            Long orderId
    ) {
    }
}
