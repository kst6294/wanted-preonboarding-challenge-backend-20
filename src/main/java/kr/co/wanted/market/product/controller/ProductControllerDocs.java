package kr.co.wanted.market.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.wanted.market.common.global.dto.PageResult;
import kr.co.wanted.market.common.global.dto.ApiResult;
import kr.co.wanted.market.common.global.dto.PageInfo;
import kr.co.wanted.market.product.dto.ProductDetail;
import kr.co.wanted.market.product.dto.ProductRegistration;
import kr.co.wanted.market.product.dto.ProductSimple;
import org.springframework.http.ResponseEntity;

@Tag(name = "상품 API", description = "상품 관련 API")
public interface ProductControllerDocs {

    @Operation(
            summary = "상품등록",
            description = "상품을 등록합니다. 회원만 등록할 수 있습니다.",
            requestBody = @RequestBody(content = @Content(
                    examples = {
                            @ExampleObject(name = "예시", value = """
                                    {
                                        "name": "example",
                                        "price": 10000,
                                        "quantity": 50
                                    }
                                    """)
                    }
            ))
    )
    ResponseEntity<ApiResult<ProductRegistration>> registerProduct(ProductRegistration productRegistration);


    @Operation(
            summary = "상품 상세조회",
            description = "상품의 상세정보를 조회합니다."
    )
    ResponseEntity<ApiResult<ProductDetail>> searchProductDetail(Long seq);


    @Operation(
            summary = "상품 목록조회",
            description = """
                    상품 목록을 조회합니다. 페이징 정보가 필요합니다.
                    
                    page: 페이지 번호 (기본값 0)
                    
                    size: 페이지당 사이즈 (기본값 5)
                    """
    )
    ResponseEntity<ApiResult<PageResult<ProductSimple>>> searchProductList(PageInfo pageInfo);

}
