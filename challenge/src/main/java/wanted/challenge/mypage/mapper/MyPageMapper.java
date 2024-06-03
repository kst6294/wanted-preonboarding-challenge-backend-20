package wanted.challenge.mypage.mapper;

import org.springframework.stereotype.Component;
import wanted.challenge.goods.entity.Goods;
import wanted.challenge.mypage.dto.response.MyPageResponseDto;
import wanted.challenge.mypage.entity.Member;
import wanted.challenge.mypage.entity.Orders;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyPageMapper {

    public List<MyPageResponseDto.orderListItemInfo> orderToOrderListItemInfo(List<Orders> orderList){
        ArrayList<MyPageResponseDto.orderListItemInfo> list = new ArrayList<>();
        for(Orders order : orderList){
            list.add(orderToOrderInfo(order));
        }
        return list;

    }


    public MyPageResponseDto.orderListItemInfo orderToOrderInfo(Orders order){
        return new MyPageResponseDto.orderListItemInfo(
                order.getOrderId(),
                order.getGoodsName(),
                order.getOrderStatus(),
                order.getQuantity()
        );
    }


    public MyPageResponseDto.sellOrderDetail orderToSellOrderDetail(Orders order , Member buyer, Goods goods){

        return new MyPageResponseDto.sellOrderDetail(
                order.getOrderId(),
                goods.getGoodsId(),
                goods.getGoodsName(),
                goods.getReservedStatus(),
                buyer.getMemberId(),
                buyer.getName(),
                order.getOrderStatus(),
                order.getQuantity(),
                order.getOrderPrice(),
                order.getOrderDate(),
                order.getConfirmDate(),
                order.getFinishDate()
        );
    }

    public MyPageResponseDto.buyOrderDetail orderToBuyOrderDetail(Orders order, Goods goods){
        return new MyPageResponseDto.buyOrderDetail(
                order.getOrderId(),
                goods.getGoodsId(),
                goods.getGoodsName(),
                goods.getReservedStatus(),
                goods.getSeller().getMemberId(),
                goods.getSeller().getName(),
                order.getOrderStatus(),
                order.getQuantity(),
                order.getOrderPrice(),
                order.getOrderDate(),
                order.getConfirmDate(),
                order.getFinishDate()
        );
    }

}
