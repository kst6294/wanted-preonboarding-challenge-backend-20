package com.wanted.preonboarding.module.product.controller;

import com.wanted.preonboarding.auth.config.AuthConstants;
import com.wanted.preonboarding.data.auth.AuthModuleHelper;
import com.wanted.preonboarding.data.product.ProductFactory;
import com.wanted.preonboarding.data.product.ProductModuleHelper;
import com.wanted.preonboarding.document.utils.DocumentLinkGenerator;
import com.wanted.preonboarding.document.utils.RestDocsTestSupport;
import com.wanted.preonboarding.module.common.dto.CustomSlice;
import com.wanted.preonboarding.module.product.core.BaseSku;
import com.wanted.preonboarding.module.product.core.Sku;
import com.wanted.preonboarding.module.product.dto.CreateProduct;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.product.filter.ItemFilter;
import com.wanted.preonboarding.module.product.mapper.ProductSliceMapperImpl;
import com.wanted.preonboarding.module.user.core.BaseUserInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static com.wanted.preonboarding.document.utils.RestDocsConfig.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest extends RestDocsTestSupport {

    @InjectMocks
    private ProductSliceMapperImpl productSliceMapper;

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



    @Test
    @DisplayName("상품 조회")
    void fetchProduct() throws Exception {
        // given
        Sku sku = ProductFactory.generateSku();

        when(productFetchService.fetchProduct(sku.getId())).thenReturn(sku);

        // when & then
        mockMvc.perform(get("/api/v1/product/{productId}", sku.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("product/get-product",
                        pathParameters(
                                parameterWithName("productId").description("상품 ID")
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
    @DisplayName("상품 목록 조회")
    void fetchProducts() throws Exception {
        // given
        List<Sku> skus = ProductFactory.generateSkus(10);
        ItemFilter filter = mock(ItemFilter.class);

        CustomSlice<Sku> customSlice = productSliceMapper.toSlice(skus, PageRequest.of(0, 5), filter);

        when(productFetchService.fetchProducts(any(ItemFilter.class), any(Pageable.class))).thenReturn(customSlice);

        // when & then
        mockMvc.perform(get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("product/get-products",

                        responseFields(
                                beneathPath("data"),
                                fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("상품 ID (PK)"),
                                fieldWithPath("content[].productName").type(JsonFieldType.STRING).description("상품 명"),
                                fieldWithPath("content[].price").type(JsonFieldType.NUMBER).description("상품 가격"),
                                fieldWithPath("content[].productStatus").type(JsonFieldType.STRING).description(DocumentLinkGenerator.generateLinkCode(DocumentLinkGenerator.DocUrl.PRODUCT_STATUS)),
                                fieldWithPath("content[].seller").type(JsonFieldType.STRING).description("상품 등록자 이메일")
                        ).and(
                                sliceDescription()
                        ),
                        responseFields(
                                beneathPath("response"),
                                statusMsg()
                        )
                ));
    }

}