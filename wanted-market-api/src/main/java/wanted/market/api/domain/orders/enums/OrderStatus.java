package wanted.market.api.domain.orders.enums;

import wanted.market.api.domain.product.enums.ProductStatus;

public enum OrderStatus {

    RESERVED("승인대기"),
    APPROVED("승인완료"),
    COMPLETED("구매확정");


    private final String status;

    OrderStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }

    public static OrderStatus addDefaultValueOf(String name){
        try{
            return OrderStatus.valueOf(name);
        } catch(IllegalArgumentException e){
            return RESERVED;
        }
    }
}
