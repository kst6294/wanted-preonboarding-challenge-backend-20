package com.exception_study.global.fixture.product_order;

import lombok.Data;

public class ProductOrderInfoFixture {

    public static TestInfo get(){
        TestInfo info = new TestInfo();
        info.setId(1L);
        info.setPrice(1000);
        info.setSellerStatus("예약중");
        info.setBuyerStatus("예약중");
        return info;
    }

    @Data
    public static class TestInfo{
        private Long id;
        private int price;
        private String sellerStatus;
        private String buyerStatus;
    }

}
