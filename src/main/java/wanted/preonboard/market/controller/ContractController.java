package wanted.preonboard.market.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wanted.preonboard.market.config.CustomUser;
import wanted.preonboard.market.domain.common.ResponseOk;
import wanted.preonboard.market.domain.product.ProductState;
import wanted.preonboard.market.domain.common.ResponseBad;
import wanted.preonboard.market.domain.product.Product;
import wanted.preonboard.market.message.ContractMessage;
import wanted.preonboard.market.message.ProductMessage;
import wanted.preonboard.market.service.Contract.ContractService;
import wanted.preonboard.market.service.Product.ProductService;

@RestController
@RequestMapping("/contract")
public class ContractController {
    private final ContractService contractService;
    private final ProductService productService;

    @Autowired
    public ContractController(ContractService contractService, ProductService productService) {
        this.contractService = contractService;
        this.productService = productService;
    }

    /**
     * 거래 생성
     * 구매자가 제품의 상세페이지에서 구매하기 버튼을 누르면 거래가 생성된다.
     *
     * @param user      구매자 정보
     * @param productId 구매 대상 제품 ID
     * @return ResponseEntity<?>
     */
    @PostMapping("")
    public ResponseEntity<?> createContract(@AuthenticationPrincipal CustomUser user, @RequestParam Integer productId) {
        Product targetProduct = productService.getProductById(productId);
        if (targetProduct == null) {
            return new ResponseBad(ProductMessage.INVALID_PRODUCT_ID).toResponse();
        }
        if (targetProduct.getState().equals(ProductState.SALES_COMPLETED)) {
            return new ResponseBad(ProductMessage.PRODUCT_ALREADY_SOLD).toResponse();
        }
        try {
            if (contractService.createContract(user.getMember().getId(), targetProduct)) {
                return new ResponseOk(ContractMessage.REGISTERED_SUCCESSFULLY).toResponse();
            } else {
                return new ResponseBad(ContractMessage.ERROR).toResponse();
            }
        } catch (DataIntegrityViolationException e) {
            return new ResponseBad(ContractMessage.CONTRACT_ALREADY_EXISTS).toResponse();
        }
    }
}
