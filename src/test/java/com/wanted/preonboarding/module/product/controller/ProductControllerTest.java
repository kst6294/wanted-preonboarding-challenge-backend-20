package com.wanted.preonboarding.module.product.controller;

import com.wanted.preonboarding.auth.config.AuthConstants;
import com.wanted.preonboarding.data.auth.AuthModuleHelper;
import com.wanted.preonboarding.data.product.ProductFactory;
import com.wanted.preonboarding.data.product.ProductModuleHelper;
import com.wanted.preonboarding.document.utils.DocumentLinkGenerator;
import com.wanted.preonboarding.document.utils.RestDocsTestSupport;
import com.wanted.preonboarding.module.product.core.BaseSku;
import com.wanted.preonboarding.module.product.dto.CreateProduct;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.user.core.BaseUserInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static com.wanted.preonboarding.document.utils.RestDocsConfig.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest extends RestDocsTestSupport {

    @Test
    @DisplayName("상품 등록")
    void createProduct() throws Exception {
        BaseUserInfo userInfo = AuthModuleHelper.toBaseUserInfo();
        when(userFindService.fetchUserInfo(anyString())).thenReturn(userInfo);

        securityUserMockSetting();

        // given
        CreateProduct createProduct = ProductModuleHelper.toCreateProduct();
        Product product = ProductFactory.generateProduct(createProduct);
        BaseSku sku = ProductModuleHelper.toSku(product);

        when(productQueryService.createProduct(any(CreateProduct.class))).thenReturn(sku);

        // when & then
        mockMvc.perform(post("/api/v1/product")
                        .header(AuthConstants.AUTHORIZATION_HEADER, AuthConstants.BEARER_PREFIX + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createProduct)))
                .andExpect(status().isOk())
                .andDo(document("product/post-product",
                        requestFields(
                                fieldWithPath("productName").type(JsonFieldType.STRING).description("상품 명")
                                        .attributes(field("constraints", "상품명은 100자를 넘어갈 수 없습니다.")),
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("상품 가격")
                                        .attributes(field("constraints", "상품 가격은 최소 0원  최대 100,000,000원 입니다."))
                        ),
                        responseFields(
                                beneathPath("data"),
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("상품 ID (PK)"),
                                fieldWithPath("productName").type(JsonFieldType.STRING).description("상품 명"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("상품 가격"),
                                fieldWithPath("productStatus").type(JsonFieldType.STRING).description(DocumentLinkGenerator.generateLinkCode(DocumentLinkGenerator.DocUrl.PRODUCT_STATUS)),
                                fieldWithPath("seller").type(JsonFieldType.STRING).description("상품 등록자 이메일")
                        ),
                        responseFields(
                                beneathPath("response"),
                                statusMsg()
                        )
                ));
    }


    @Test
    @DisplayName("상품 등록 실패 - 비회원")
    void createProduct_unAuthorization() throws Exception {
        // given
        CreateProduct createProduct = ProductModuleHelper.toCreateProduct();

        // when & then
        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createProduct)))
                .andExpect(status().isUnauthorized())
                .andDo(document("product/post-product-unauthorized",
                        requestFields(
                                fieldWithPath("productName").type(JsonFieldType.STRING).description("상품 명")
                                        .attributes(field("constraints", "상품명은 100자를 넘어갈 수 없습니다.")),
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("상품 가격")
                                        .attributes(field("constraints", "상품 가격은 최소 0원  최대 100,000,000원 입니다."))
                        )
                ));
    }


}