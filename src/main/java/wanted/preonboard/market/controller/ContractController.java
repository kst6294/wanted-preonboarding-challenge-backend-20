package wanted.preonboard.market.controller;

import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import wanted.preonboard.market.config.CustomUser;
import wanted.preonboard.market.domain.common.ResponseBad;
import wanted.preonboard.market.domain.common.ResponseOk;
import wanted.preonboard.market.domain.contract.Contract;
import wanted.preonboard.market.domain.contract.ContractState;
import wanted.preonboard.market.domain.product.Product;
import wanted.preonboard.market.domain.product.ProductState;
import wanted.preonboard.market.domain.product.dto.ProductUpdateDto;
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
                ProductUpdateDto productUpdateDto = new ProductUpdateDto();
                productUpdateDto.setState(JsonNullable.of(ProductState.RESERVED));
                if (productService.updateProductById(productId, productUpdateDto))
                    return new ResponseOk(ContractMessage.REGISTERED_SUCCESSFULLY).toResponse();
            }
            return new ResponseBad(ContractMessage.ERROR).toResponse();
        } catch (DataIntegrityViolationException e) {
            return new ResponseBad(ContractMessage.CONTRACT_ALREADY_EXISTS).toResponse();
        }
    }

    @GetMapping("/purchased")
    public ResponseEntity<?> getPurchasedContractsByUserId(@AuthenticationPrincipal CustomUser user) {
        return new ResponseOk(contractService.getPurchasedContractsByUserId(user.getMember().getId())).toResponse();
    }

    @GetMapping("/reservation")
    public ResponseEntity<?> getReservationContractsByUserId(@AuthenticationPrincipal CustomUser user) {
        return new ResponseOk(contractService.getReservationContractsByUserId(user.getMember().getId())).toResponse();
    }

    @PatchMapping("/approval/{contractId}")
    public ResponseEntity<?> updateContract(@AuthenticationPrincipal CustomUser user, @PathVariable Integer contractId) {
        Contract contract = contractService.getContractById(contractId);
        if (contract == null) {
            return new ResponseBad(ContractMessage.INVALID_CONTRACT_ID).toResponse();
        }
        if (!contract.getSellerId().equals(user.getMember().getId())) {
            return new ResponseBad(ContractMessage.CONTRACT_NOT_AUTHORIZED).toResponse();
        }
        if (contract.getState().compareTo(ContractState.APPROVED) == 0) {
            return new ResponseBad(ContractMessage.ALREADY_APPROVED).toResponse();
        }
        if (contractService.approveContract(contract)) {
            ProductUpdateDto productUpdateDto = new ProductUpdateDto();
            productUpdateDto.setState(JsonNullable.of(ProductState.SALES_COMPLETED));
            if (productService.updateProductById(contract.getProductId(), productUpdateDto)) {
                return new ResponseOk(ContractMessage.APPROVED_SUCCESSFULLY).toResponse();
            }

            return new ResponseBad(ProductMessage.ERROR).toResponse();
        } else {
            return new ResponseBad(ContractMessage.ERROR).toResponse();
        }
    }
}
