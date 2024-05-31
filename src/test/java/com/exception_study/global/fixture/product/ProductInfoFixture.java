package com.exception_study.global.fixture.product;

import lombok.Data;

public class ProductInfoFixture {

    public static TestInfo get(){
        TestInfo info = new TestInfo();
        info.setId(1L);
        info.setName("테스트상품");
        info.setPrice(1000);
        info.setStatus("판매중");
        info.setQuantity(3);
        return info;
    }

    @Data
    public static class TestInfo{
        private Long id;
        private String name;
        private int price;
        private String status;
        private int quantity;
    }

}
