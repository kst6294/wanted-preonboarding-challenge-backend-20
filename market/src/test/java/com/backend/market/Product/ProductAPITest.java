package com.backend.market.Product;

import com.backend.market.Common.auth.UserDetailsImpl;
import com.backend.market.DAO.Entity.Member;
import com.backend.market.DAO.Entity.Product;
import com.backend.market.DAO.Entity.Status;
import com.backend.market.Request.ProductReq;
import com.backend.market.Repository.MemberRepository;
import com.backend.market.Service.Product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductAPITest {

    @Autowired
    private ProductService productService;

    @Autowired
    private MemberRepository memberRepository;

    UserDetailsImpl userDetails = new UserDetailsImpl();
    @Test
    void testProductAddAPI()
    {
        Member member = new Member();
        member.setName("신짱구");
        member.setPassword("1234");
        this.memberRepository.save(member);

        ProductReq productReq = new ProductReq();
        productReq.setProduct_name("피자");
        productReq.setPrice(2000);
        productReq.setMember(member);
        productReq.setQuantity(3);

        ProductReq productReq2 = new ProductReq();
        productReq2.setProduct_name("마라탕");
        productReq2.setPrice(1500);
        productReq2.setMember(member);
        productReq2.setQuantity(2);

        //this.productService.addProduct(productReq);
        //this.productService.addProduct(productReq2);
    }

    @Test
    void testProductFindAllAPI() throws Exception {

        //모든 상품목록 조회
        List<Product> lists = this.productService.getList();
        assertEquals(3,lists.size());
        //특정 상품 상세조회
        Product product = this.productService.getProduct(1L,userDetails);
        assertEquals(product.getProduct_name(),"햄버거");

        //예약중인 상품목록 조회
        List<Product> revervations = this.productService.getReservationsList(2L,userDetails);
        assertEquals(revervations.size(),1);
        //판매완료 상품목록 조회
        List<Product> bougths = this.productService.getBoughtList(2L,userDetails);
        assertEquals(bougths.size(),1);
    }

    @Test
    void testUpdateStatusAPI()
    {
        ProductReq productReq = new ProductReq();
        productReq.setProduct_id(2L);
        productReq.setStatus(Status.complete);
        this.productService.updateStatus(productReq,userDetails);
    }
}
