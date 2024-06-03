package wanted.challenge.mypage.mapper;

import org.springframework.stereotype.Component;
import wanted.challenge.goods.entity.Goods;
import wanted.challenge.mypage.dto.response.MyPageResponseDto;
import wanted.challenge.mypage.entity.Member;
import wanted.challenge.mypage.entity.OrderStatus;
import wanted.challenge.mypage.entity.Orders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static wanted.challenge.goods.mapper.GoodsMapper.toGoodsStatus;

@Component
public class MyPageMapper {

    public List<MyPageResponseDto.orderListItemInfo> orderToOrderListItemInfo(List<Orders> orderList) {
        ArrayList<MyPageResponseDto.orderListItemInfo> list = new ArrayList<>();
        for (Orders order : orderList) {
            list.add(orderToOrderInfo(order));
        }
        return list;

    }


    public MyPageResponseDto.orderListItemInfo orderToOrderInfo(Orders order) {
        return new MyPageResponseDto.orderListItemInfo(
                order.getOrderId(),
                order.getGoodsName(),
                toOrderStatusResponse(order.getOrderStatus()),
                order.getQuantity()
        );
    }


    public MyPageResponseDto.sellOrderDetail orderToSellOrderDetail(Orders order, Member buyer, Goods goods) {
        return new MyPageResponseDto.sellOrderDetail(
                order.getOrderId(),
                goods.getGoodsId(),
                goods.getGoodsName(),
                toGoodsStatus(goods.getReservedStatus()),

                buyer.getMemberId(),
                buyer.getName(),

                toOrderStatusResponse(order.getOrderStatus()),
                order.getQuantity(),
                order.getOrderPrice(),
                order.getOrderDate(),
                order.getConfirmDate(),
                order.getFinishDate()
        );
    }

    public MyPageResponseDto.buyOrderDetail orderToBuyOrderDetail(Orders order, Goods goods) {
        return new MyPageResponseDto.buyOrderDetail(
                order.getOrderId(),
                goods.getGoodsId(),
                goods.getGoodsName(),
                toGoodsStatus(goods.getReservedStatus()),

                goods.getSeller().getMemberId(),
                goods.getSeller().getName(),

                toOrderStatusResponse(order.getOrderStatus()),
                order.getQuantity(),
                order.getOrderPrice(),
                order.getOrderDate(),
                order.getConfirmDate(),
                order.getFinishDate()
        );
    }


    public static MyPageResponseDto.OrderStatus toOrderStatusResponse(OrderStatus orderStatus) {
        return switch (orderStatus) {
            case ORDER -> MyPageResponseDto.OrderStatus.승인대기;
            case CONFIRM -> MyPageResponseDto.OrderStatus.판매승인;
            case FINISH -> MyPageResponseDto.OrderStatus.구매확정;
            default -> throw new IllegalArgumentException("주문 상태 오류");
        };
    }

    // 주문 상태에 따른 시간 반환
    public static LocalDateTime toOrderStatusTime(OrderStatus orderStatus, Orders order) {
        return switch (orderStatus) {
            case ORDER -> order.getOrderDate();
            case CONFIRM -> order.getConfirmDate();
            case FINISH -> order.getFinishDate();
            default -> throw new IllegalArgumentException("주문 상태 오류");
        };
    }
}
