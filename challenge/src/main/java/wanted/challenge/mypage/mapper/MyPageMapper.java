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
        MyPageResponseDto.orderListItemInfo orderListItemInfo = new MyPageResponseDto.orderListItemInfo();
        orderListItemInfo.setOrderId(order.getOrderId());
        orderListItemInfo.setGoodsName(order.getGoodsName());
        orderListItemInfo.setOrderStatus(order.getOrderStatus());
        orderListItemInfo.setQuantity(order.getQuantity());
        return orderListItemInfo;
    }


    public MyPageResponseDto.sellOrderDetail orderToSellOrderDetail(Orders order , Member buyer, Goods goods){
        return null;
    }

    public MyPageResponseDto.buyOrderDetail orderToBuyOrderDetail(Orders order , Member buyer, Goods goods){
        return null;
    }

}
