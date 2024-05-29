package wanted.market.api.domain.product.enums;

public enum ProductStatus {

    SALE("판매중"),
    RESERVED("예약중"),
    COMPLETED("완료");


    private final String status;

    ProductStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }
    public static ProductStatus addDefaultValueOf(String name){
        try{
            return ProductStatus.valueOf(name);
        } catch(IllegalArgumentException e){
            return SALE;
        }
    }
}
