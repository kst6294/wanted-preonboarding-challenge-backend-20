package wanted.preonboard.market.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import wanted.preonboard.market.config.CustomUser;
import wanted.preonboard.market.domain.common.ResponseBad;
import wanted.preonboard.market.domain.common.ResponseOk;
import wanted.preonboard.market.domain.contract.dto.ContractResDto;
import wanted.preonboard.market.domain.product.dto.ProductInsertDto;
import wanted.preonboard.market.domain.product.dto.ProductResDto;
import wanted.preonboard.market.domain.product.dto.ProductUpdateDto;
import wanted.preonboard.market.message.ProductMessage;
import wanted.preonboard.market.service.Contract.ContractService;
import wanted.preonboard.market.service.Product.ProductService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ContractService contractService;

    @Autowired
    public ProductController(ProductService productService, ContractService contractService) {
        this.productService = productService;
        this.contractService = contractService;
    }

    @PostMapping("")
    public ResponseEntity<?> createProduct(@AuthenticationPrincipal CustomUser user, @RequestBody ProductInsertDto product) {
        try {
            if (productService.createProduct(user.getMember().getId(), product)) {
                return new ResponseOk(ProductMessage.REGISTERED_SUCCESSFULLY).toResponse();
            } else {
                return new ResponseBad(ProductMessage.ERROR).toResponse();
            }
        } catch (Exception e) {
            return new ResponseBad(ProductMessage.ERROR + e.getMessage()).toResponse();
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getProducts() {
        try {
            return new ResponseOk(productService.getProducts()).toResponse();
        } catch (Exception e) {
            return new ResponseBad(ProductMessage.FIND_ERROR + e.getMessage()).toResponse();
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@AuthenticationPrincipal CustomUser user, @PathVariable Integer productId) {
        try {
            ProductResDto product = productService.getProductById(productId);

            if (user == null) {
                return new ResponseOk(Map.of("product", product)).toResponse();
            }

            // 로그인한 유저가 판매자일 경우에는 해당 상품에 대한 거래 내역을 함께 반환
            if (product.getSellerId().compareTo(user.getMember().getId()) == 0) {
                List<ContractResDto> contracts = contractService.getContractsByProductId(productId);
                return new ResponseOk(Map.of("product", product, "contracts", contracts)).toResponse();
            }

            // 로그인한 유저와 판매자 간에 거래 내역이 있는 경우에는 함께 반환
            List<ContractResDto> contracts = contractService.getContractsByUserIds(user.getMember().getId(), product.getSellerId());
            if (!contracts.isEmpty()) {
                return new ResponseOk(Map.of("product", product, "contracts", contracts)).toResponse();
            }

            return new ResponseOk(Map.of("product", product)).toResponse();
        } catch (NullPointerException e) {
            return new ResponseBad(ProductMessage.INVALID_PRODUCT_ID).toResponse();
        } catch (Exception e) {
            return new ResponseBad(ProductMessage.FIND_ERROR + e.getMessage()).toResponse();
        }
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<?> updateProductById(@PathVariable Integer productId, @RequestBody ProductUpdateDto product) {
        try {
            return new ResponseOk(productService.updateProductById(productId, product)).toResponse();
        } catch (NullPointerException e) {
            return new ResponseBad(ProductMessage.INVALID_PRODUCT_ID).toResponse();
        } catch (Exception e) {
            return new ResponseBad(ProductMessage.ERROR + e.getMessage()).toResponse();
        }
    }
}
