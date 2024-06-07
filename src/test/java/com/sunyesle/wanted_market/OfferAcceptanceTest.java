package com.sunyesle.wanted_market;

import com.sunyesle.wanted_market.dto.*;
import com.sunyesle.wanted_market.enums.OfferStatus;
import com.sunyesle.wanted_market.enums.ProductStatus;
import com.sunyesle.wanted_market.repository.MemberRepository;
import com.sunyesle.wanted_market.repository.OfferRepository;
import com.sunyesle.wanted_market.repository.ProductRepository;
import com.sunyesle.wanted_market.support.AcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    void 제품구매를_요청한다() {
        CreateOfferRequest createOfferRequest = new CreateOfferRequest(savedProductId, 1);

        ExtractableResponse<Response> response = 구매_요청(createOfferRequest, buyerToken);

        등록에_성공한다(response);
        OfferResponse offerResponse = response.as(OfferResponse.class);
        ExtractableResponse<Response> 제품_조회_결과 = 제품_조회_요청(offerResponse.getId());
        제품_구매가능_수량이_변경된다(제품_조회_결과, 1);
    }

    @Test
    void 모든_제품이_구매요청되면_제품상태가_RESERVED로_변경된다() {
        CreateOfferRequest createOfferRequest = new CreateOfferRequest(savedProductId, 2);

        ExtractableResponse<Response> response = 구매_요청(createOfferRequest, buyerToken);

        등록에_성공한다(response);
        OfferResponse offerResponse = response.as(OfferResponse.class);
        ExtractableResponse<Response> 제품_조회_결과 = 제품_조회_요청(offerResponse.getId());
        제품_구매가능_수량이_변경된다(제품_조회_결과, 0);
        제품_상태가_변경된다(제품_조회_결과, ProductStatus.RESERVED);
    }

    @Test
    void 제품을_중복으로_예약할_경우_실패한다() {
        CreateOfferRequest createOfferRequest = new CreateOfferRequest(savedProductId, 1);

        구매_요청(createOfferRequest, buyerToken);
        ExtractableResponse<Response> response = 구매_요청(createOfferRequest, buyerToken);

        잘못된_요청으로_인해_요청에_실패한다(response);
    }

    @Test
    void 제품_예약_요청을_승인한다() {
        CreateOfferRequest createOfferRequest = new CreateOfferRequest(savedProductId, 1);
        Long offerId = 구매_요청(createOfferRequest, buyerToken).as(OfferResponse.class).getId();

        ExtractableResponse<Response> response = 구매_승인처리_요청(offerId, sellerToken);

        수정에_성공한다(response);
        OfferResponse offerResponse = response.as(OfferResponse.class);
        assertThat(offerResponse.getStatus()).isEqualTo(OfferStatus.ACCEPTED);
    }

    @Test
    void 모든_제품이_판매되면_제품상태가_COMPLETED로_변경된다() {
        CreateOfferRequest createOfferRequest = new CreateOfferRequest(savedProductId, 2);
        Long offerId = 구매_요청(createOfferRequest, buyerToken).as(OfferResponse.class).getId();

        ExtractableResponse<Response> response = 구매_승인처리_요청(offerId, sellerToken);

        수정에_성공한다(response);
        OfferResponse offerResponse = response.as(OfferResponse.class);
        ExtractableResponse<Response> 제품_조회_결과 = 제품_조회_요청(offerResponse.getId());
        제품_상태가_변경된다(제품_조회_결과, ProductStatus.COMPLETED);
    }

    @Test
    void 제품_예약_요청을_거절한다() {
        CreateOfferRequest createOfferRequest = new CreateOfferRequest(savedProductId, 2);
        Long offerId = 구매_요청(createOfferRequest, buyerToken).as(OfferResponse.class).getId();

        ExtractableResponse<Response> response = 구매_거절처리_요청(offerId, sellerToken);

        수정에_성공한다(response);
        OfferResponse offerResponse = response.as(OfferResponse.class);
        assertThat(offerResponse.getStatus()).isEqualTo(OfferStatus.DECLINED);
        ExtractableResponse<Response> 제품_조회_결과 = 제품_조회_요청(offerResponse.getId());
        제품_상태가_변경된다(제품_조회_결과, ProductStatus.AVAILABLE);
    }
}
