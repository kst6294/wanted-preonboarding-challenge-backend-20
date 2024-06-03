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
        MyPageResponseDto.sellOrderDetail sellOrderDetail = new MyPageResponseDto.sellOrderDetail();
        sellOrderDetail.setOrderId(order.getOrderId());
        sellOrderDetail.setGoodsId(goods.getGoodsId());
        sellOrderDetail.setGoodsName(goods.getGoodsName());
        sellOrderDetail.setGoodsStatus(goods.getReservedStatus());

        sellOrderDetail.setBuyerId(buyer.getMemberId());
        sellOrderDetail.setBuyerName(buyer.getName());

        sellOrderDetail.setOrderStatus(order.getOrderStatus());
        sellOrderDetail.setQuantity(order.getQuantity());
        sellOrderDetail.setOrderPrice(order.getOrderPrice());
        sellOrderDetail.setOrderDate(order.getOrderDate());
        sellOrderDetail.setConfirmDate(order.getConfirmDate());
        sellOrderDetail.setFinishDate(order.getFinishDate());
        return sellOrderDetail;
    }

    public MyPageResponseDto.buyOrderDetail orderToBuyOrderDetail(Orders order , Member buyer, Goods goods){
        MyPageResponseDto.buyOrderDetail buyOrderDetail = new MyPageResponseDto.buyOrderDetail();
        buyOrderDetail.setOrderId(order.getOrderId());
        buyOrderDetail.setGoodsId(goods.getGoodsId());
        buyOrderDetail.setGoodsName(goods.getGoodsName());
        buyOrderDetail.setGoodsStatus(goods.getReservedStatus());

        buyOrderDetail.setSellerId(goods.getSeller().getMemberId());
        buyOrderDetail.setSellerName(goods.getSeller().getName());

        buyOrderDetail.setOrderStatus(order.getOrderStatus());
        buyOrderDetail.setQuantity(order.getQuantity());
        buyOrderDetail.setOrderPrice(order.getOrderPrice());
        buyOrderDetail.setOrderDate(order.getOrderDate());
        buyOrderDetail.setConfirmDate(order.getConfirmDate());
        buyOrderDetail.setFinishDate(order.getFinishDate());
        return buyOrderDetail;
    }

}
