package market.market.domain.product.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import market.market.domain.product.presentation.dto.requset.CreateProduct;
import market.market.domain.product.presentation.dto.response.QueryProductDetailsResponse;
import market.market.domain.product.presentation.dto.response.QueryProductListResponse;
import market.market.domain.product.service.CreateProductService;
import market.market.domain.product.service.QueryProductDetailService;
import market.market.domain.product.service.QueryProductListService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
@Tag(name = "Product API", description = "Product에 대한 API입니다.")
public class ProductController {
    private final CreateProductService createProductService;
    private final QueryProductDetailService queryProductDetailService;
    private final QueryProductListService queryProductListService;

    @Operation(summary = "상품 등록", description = "상품을 등록합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public void createProduct(@Valid @RequestBody CreateProduct request) { createProductService.execute(request);}

    @Operation(summary = "상품 목록", description = "상품 목록을 확인 할 수 있습니다.")
    @GetMapping("/list")
    public List<QueryProductListResponse> listProducts() {return queryProductListService.execute();}

    @Operation(summary = "상품 자세히 보기", description = "상품을 자세하게 봅니다.")
    @GetMapping("/detail/{id}")
    public QueryProductDetailsResponse detail(@PathVariable Long id) {return queryProductDetailService.execute(id);}
}
