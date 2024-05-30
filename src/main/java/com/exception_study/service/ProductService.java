package com.exception_study.service;

import com.exception_study.dto.ProductDto;
import com.exception_study.dto.request.RegisterRequest;
import com.exception_study.dto.response.DetailsWithHistoryResponse;
import com.exception_study.dto.response.HistoryResponse;
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

    public void register(RegisterRequest dto, String userId) {
        UserAccount userAccount = userAccountRepository.findById(userId).orElseThrow(
                () -> new StudyApplicationException(ErrorCode.USER_NOT_FOUND)
        );
        Product product = Product.of(dto.getName(), dto.getPrice(), userAccount.getUserName());
        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public ProductDto getDetails(int id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new StudyApplicationException(ErrorCode.PRODUCT_NOT_FOUND)
        );
        return ProductDto.from(product);
    }

    @Transactional(readOnly = true)
    public DetailsWithHistoryResponse getDetails(int id, String userId) {
        UserAccount userAccount = userAccountRepository.findById(userId).orElseThrow(
                () -> new StudyApplicationException(ErrorCode.USER_NOT_FOUND)
        );
        Product product = productRepository.findById(id).orElseThrow(
                () -> new StudyApplicationException(ErrorCode.PRODUCT_NOT_FOUND)
        );

        List<ProductDto> history = productRepository.findTransaction_History(
                product.getSeller(), userAccount.getUserName()).stream().map(ProductDto::from).toList();

        return DetailsWithHistoryResponse.of(ProductDto.from(product),history);
    }

    public ProductDto reserve(int id, String userId) {
        UserAccount userAccount = userAccountRepository.findById(userId).orElseThrow(
                () -> new StudyApplicationException(ErrorCode.USER_NOT_FOUND)
        );
        Product product = productRepository.findById(id).orElseThrow(
                () -> new StudyApplicationException(ErrorCode.PRODUCT_NOT_FOUND)
        );
        product.setBuyer(userAccount.getUserName());
        product.setStatus("예약중");

        return ProductDto.from(product);
    }

    public void approve(int id, String userId) {
        UserAccount userAccount = userAccountRepository.findById(userId).orElseThrow(
                () -> new StudyApplicationException(ErrorCode.USER_NOT_FOUND)
        );
        Product product = productRepository.findById(id).orElseThrow(
                () -> new StudyApplicationException(ErrorCode.PRODUCT_NOT_FOUND)
        );
        if (!product.getSeller().equals(userAccount.getUserName())) {
            throw new StudyApplicationException(ErrorCode.INVALID_PERMISSION);
        }else if(!product.getStatus().equals("예약중")){
            throw new StudyApplicationException(ErrorCode.INVALID_PERMISSION);
        }
        else {
            product.setStatus("완료");
        }

    }

    public HistoryResponse history(String userId) {
        UserAccount userAccount = userAccountRepository.findById(userId).orElseThrow(
                () -> new StudyApplicationException(ErrorCode.USER_NOT_FOUND)
        );
        String userName = userAccount.getUserName();

        List<ProductDto> buyList = productRepository.findAllByBuyer(userName).stream().map(ProductDto::from).toList();
        List<ProductDto> reservedList = productRepository.findReservedProducts(userName).stream().map(ProductDto::from).toList();

        return HistoryResponse.of(buyList,reservedList);
    }
}
