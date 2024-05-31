package com.exception_study.api.product.service;

import com.exception_study.api.product.dto.ProductDto;
import com.exception_study.api.product.dto.response.DetailsWithHistoryResponse;
import com.exception_study.api.product.entity.Product;
import com.exception_study.api.product.repository.ProductRepository;
import com.exception_study.api.product_order.dto.ProductOrderDto;
import com.exception_study.api.user_account.dto.request.RegisterRequest;
import com.exception_study.api.user_account.entity.UserAccount;
import com.exception_study.global.exception.ErrorCode;
import com.exception_study.global.exception.StudyApplicationException;
import com.exception_study.api.product_order.repository.ProductOrderRepository;
import com.exception_study.api.user_account.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserAccountRepository userAccountRepository;
    private final ProductOrderRepository productOrderRepository;

    @Transactional(readOnly = true)
    public List<ProductDto> getList() {
        return productRepository.findAll().stream().map(ProductDto::from).toList();
    }

    public void register(RegisterRequest dto, String userId) {
        UserAccount userAccount = userAccountRepository.findById(userId).orElseThrow(
                () -> new StudyApplicationException(ErrorCode.USER_NOT_FOUND)
        );
        Product product = Product.of(dto.getName(), dto.getPrice(),dto.getQuantity(),userAccount);
        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public ProductDto getDetails(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new StudyApplicationException(ErrorCode.PRODUCT_NOT_FOUND)
        );
        return ProductDto.from(product);
    }

    @Transactional(readOnly = true)
    public DetailsWithHistoryResponse getDetails(Long id, String userId) {
        UserAccount userAccount = userAccountRepository.findById(userId).orElseThrow(
                () -> new StudyApplicationException(ErrorCode.USER_NOT_FOUND)
        );
        Product product = productRepository.findById(id).orElseThrow(
                () -> new StudyApplicationException(ErrorCode.PRODUCT_NOT_FOUND)
        );

        List<ProductOrderDto> history = productOrderRepository.findTransaction_History(product.getSeller().getUserId(),userAccount.getUserId())
                .stream().map(ProductOrderDto::from).toList();

        return DetailsWithHistoryResponse.of(ProductDto.from(product),history);
    }


}
