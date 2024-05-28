package com.backend.market.PurchaseList;

import com.backend.market.Request.ProductReq;
import com.backend.market.Service.purchaseList.PurchaseListService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor
public class PurchaseListTest {

    @Autowired
    private final PurchaseListService purchaseListService;

    @Test
    void testAPI()
    {

    }
}
