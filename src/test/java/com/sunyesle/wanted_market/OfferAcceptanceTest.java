package com.sunyesle.wanted_market;

import com.sunyesle.wanted_market.global.enums.OfferStatus;
import com.sunyesle.wanted_market.global.enums.ProductStatus;
import com.sunyesle.wanted_market.member.MemberRepository;
import com.sunyesle.wanted_market.member.dto.SigninRequest;
import com.sunyesle.wanted_market.member.dto.SigninResponse;
import com.sunyesle.wanted_market.member.dto.SignupRequest;
import com.sunyesle.wanted_market.offer.OfferRepository;
import com.sunyesle.wanted_market.offer.dto.CreateOfferRequest;
import com.sunyesle.wanted_market.offer.dto.OfferDetailResponse;
import com.sunyesle.wanted_market.offer.dto.OfferResponse;
import com.sunyesle.wanted_market.product.ProductRepository;
import com.sunyesle.wanted_market.product.dto.ProductRequest;
import com.sunyesle.wanted_market.product.dto.ProductResponse;
import com.sunyesle.wanted_market.support.AcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.sunyesle.wanted_market.support.AuthSteps.로그인_요청;
import static com.sunyesle.wanted_market.support.AuthSteps.회원가입_요청;
import static com.sunyesle.wanted_market.support.CommonSupporter.*;
import static com.sunyesle.wanted_market.support.OfferSteps.*;
import static com.sunyesle.wanted_market.support.ProductSteps.*;
import static org.assertj.core.api.Assertions.assertThat;

class OfferAcceptanceTest extends AcceptanceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OfferRepository offerRepository;

    private String sellerToken;
    private String buyerToken;
    private Long savedProductId;

    @BeforeEach
    public void setUp() {
        super.setUp();
        memberRepository.deleteAll();
        productRepository.deleteAll();
        offerRepository.deleteAll();

        String sellerEmail = "seller@email.com";
        String sellerPassword = "password1";
        회원가입_요청(new SignupRequest("판매자", sellerEmail, sellerPassword));
        sellerToken = 로그인_요청(new SigninRequest(sellerEmail, sellerPassword)).as(SigninResponse.class).getToken();

        String buyerEmail = "buyer@email.com";
        String buyerPassword = "password2";
        회원가입_요청(new SignupRequest("구매자", buyerEmail, buyerPassword));
        buyerToken = 로그인_요청(new SigninRequest(buyerEmail, buyerPassword)).as(SigninResponse.class).getToken();

        savedProductId = 제품_등록_요청(sellerToken, new ProductRequest("스위치", 300000, 2)).as(ProductResponse.class).getId();
    }

    @Test
    void 구매를_요청한다() {
        CreateOfferRequest createOfferRequest = new CreateOfferRequest(savedProductId, 2);

        ExtractableResponse<Response> response = 구매_요청(createOfferRequest, buyerToken);

        등록에_성공한다(response);
        ExtractableResponse<Response> 제품_조회_결과 = 제품_조회_요청(savedProductId);
        제품_예약된_수량이_변경된다(제품_조회_결과, 0);
        제품_구매가능_수량이_변경된다(제품_조회_결과, 2);
        제품_상태가_변경된다(제품_조회_결과, ProductStatus.AVAILABLE);
    }

    @Test
    void 중복으로_구매를_요청할_경우_실패한다() {
        CreateOfferRequest createOfferRequest = new CreateOfferRequest(savedProductId, 1);

        구매_요청(createOfferRequest, buyerToken);
        ExtractableResponse<Response> response = 구매_요청(createOfferRequest, buyerToken);

        잘못된_요청으로_인해_요청에_실패한다(response);
    }

    @Test
    void 구매요청을_승인한다() {
        CreateOfferRequest createOfferRequest = new CreateOfferRequest(savedProductId, 2);
        Long offerId = 구매_요청(createOfferRequest, buyerToken).as(OfferResponse.class).getId();

        ExtractableResponse<Response> response = 구매_승인처리_요청(offerId, sellerToken);

        수정에_성공한다(response);
        ExtractableResponse<Response> 제품_조회_결과 = 제품_조회_요청(savedProductId);
        제품_예약된_수량이_변경된다(제품_조회_결과, 2);
        제품_구매가능_수량이_변경된다(제품_조회_결과, 0);
        제품_상태가_변경된다(제품_조회_결과, ProductStatus.RESERVED);
    }

    @Test
    void 본인이_받지않은_구매요청을_승인할_경우_실패한다() {
        CreateOfferRequest createOfferRequest = new CreateOfferRequest(savedProductId, 2);
        Long offerId = 구매_요청(createOfferRequest, buyerToken).as(OfferResponse.class).getId();

        ExtractableResponse<Response> response = 구매_승인처리_요청(offerId, buyerToken);

        접근_금지로_인해_요청에_실패한다(response);
    }

    @Test
    void 구매요청을_거절한다() {
        CreateOfferRequest createOfferRequest = new CreateOfferRequest(savedProductId, 2);
        Long offerId = 구매_요청(createOfferRequest, buyerToken).as(OfferResponse.class).getId();

        ExtractableResponse<Response> response = 구매_거절처리_요청(offerId, sellerToken);

        수정에_성공한다(response);
        ExtractableResponse<Response> 제품_조회_결과 = 제품_조회_요청(savedProductId);
        제품_예약된_수량이_변경된다(제품_조회_결과, 0);
        제품_구매가능_수량이_변경된다(제품_조회_결과, 2);
        제품_상태가_변경된다(제품_조회_결과, ProductStatus.AVAILABLE);
    }

    @Test
    void 구매요청을_확정한다() {
        CreateOfferRequest createOfferRequest = new CreateOfferRequest(savedProductId, 2);
        Long offerId = 구매_요청(createOfferRequest, buyerToken).as(OfferResponse.class).getId();
        구매_승인처리_요청(offerId, sellerToken);

        ExtractableResponse<Response> response = 구매_확정처리_요청(offerId, buyerToken);

        수정에_성공한다(response);
        ExtractableResponse<Response> 제품_조회_결과 = 제품_조회_요청(savedProductId);
        제품_예약된_수량이_변경된다(제품_조회_결과, 0);
        제품_구매가능_수량이_변경된다(제품_조회_결과, 0);
        제품_상태가_변경된다(제품_조회_결과, ProductStatus.COMPLETED);
    }

    @Test
    void 내_요청목록을_조회한다() {
        CreateOfferRequest createOfferRequest = new CreateOfferRequest(savedProductId, 2);
        구매_요청(createOfferRequest, buyerToken);

        ExtractableResponse<Response> response = 요청_조회_요청(buyerToken);

        List<OfferDetailResponse> products = response.jsonPath().getList(".", OfferDetailResponse.class);
        assertThat(products).hasSize(1);
    }

    @Test
    void 내가_받은_요청목록을_조회한다() {
        CreateOfferRequest createOfferRequest = new CreateOfferRequest(savedProductId, 2);
        구매_요청(createOfferRequest, buyerToken);

        ExtractableResponse<Response> response = 받은요청_조회_요청(sellerToken);

        List<OfferDetailResponse> products = response.jsonPath().getList(".", OfferDetailResponse.class);
        assertThat(products).hasSize(1);
    }
}
