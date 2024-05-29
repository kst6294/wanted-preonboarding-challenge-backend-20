package wanted.market.api.domain.orders.enums;

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
}
