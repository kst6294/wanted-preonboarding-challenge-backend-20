package com.exception_study.service;

import com.exception_study.dto.ProductDto;
import com.exception_study.dto.request.RegisterRequest;
import com.exception_study.entity.Product;
import com.exception_study.entity.UserAccount;
import com.exception_study.exception.ErrorCode;
import com.exception_study.exception.StudyApplicationException;
import com.exception_study.repository.ProductRepository;
import com.exception_study.repository.UserAccountRepository;
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

    @Transactional(readOnly = true)
    public List<ProductDto> getList() {
        return productRepository.findAll().stream().map(ProductDto::from).toList();
    }

    public void register(RegisterRequest dto, String userId){
        UserAccount userAccount = userAccountRepository.findById(userId).orElseThrow(
                () -> new StudyApplicationException(ErrorCode.USER_NOT_FOUND)
        );
        Product product = Product.of(dto.getName(), dto.getPrice(),userAccount.getUserName());
        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public ProductDto getDetails(int id){
        Product product = productRepository.findById(id).orElseThrow(
                () -> new StudyApplicationException(ErrorCode.PRODUCT_NOT_FOUND)
        );
        return ProductDto.from(product);
    }

    public ProductDto buy(int id, String userId){
        UserAccount userAccount = userAccountRepository.findById(userId).orElseThrow(
                () -> new StudyApplicationException(ErrorCode.USER_NOT_FOUND)
        );

        Product product = productRepository.findById(id).orElseThrow(
                () -> new StudyApplicationException(ErrorCode.PRODUCT_NOT_FOUND)
        );
        product.setBuyer(userAccount.getUserName());

        return ProductDto.from(product);
    }

}
