package com.wanted.demo.domain.product.service;

import com.wanted.demo.domain.exception.exception.ProductException;
import com.wanted.demo.domain.exception.exception.UserException;
import com.wanted.demo.domain.exception.responseCode.ProductExceptionResponseCode;
import com.wanted.demo.domain.exception.responseCode.UserExceptionResponseCode;
import com.wanted.demo.domain.product.dto.request.ProductPurchaseRequestDTO;
import com.wanted.demo.domain.product.dto.request.ProductRegisterRequestDTO;
import com.wanted.demo.domain.product.dto.response.ProductDetailResponseDTO;
import com.wanted.demo.domain.product.dto.response.ProductPurchaseResponseDTO;
import com.wanted.demo.domain.product.dto.response.ProductRegisterResponseDTO;
import com.wanted.demo.domain.product.dto.response.ProductResponseDTO;
import com.wanted.demo.domain.product.entity.Product;
import com.wanted.demo.domain.product.entity.State;
import com.wanted.demo.domain.product.repository.ProductRepository;
import com.wanted.demo.domain.statements.dto.response.StatementsHistoryResponseDTO;
import com.wanted.demo.domain.statements.entity.Statements;
import com.wanted.demo.domain.statements.repository.StatementsRepository;
import com.wanted.demo.domain.user.entity.User;
import com.wanted.demo.domain.user.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final StatementsRepository statementsRepository;

    //상품등록
    @Transactional
    public ProductRegisterResponseDTO registerProduct(ProductRegisterRequestDTO productRegisterRequestDTO, Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new UserException(UserExceptionResponseCode.NOT_EXISTS_USER, id+"가 존재하지 않습니다."));

        Product product = productRegisterRequestDTO.toEntity(user);

        Product registerProduct = productRepository.save(product);

        return ProductRegisterResponseDTO.toDTO(registerProduct);
    }

    //목록 조회
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAllProducts (){
        return productRepository.findAllProducts();
    }

    //상세 조회
    @Transactional(readOnly = true)
    public ProductDetailResponseDTO findProduct(Long userId, Long productId){
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductException(ProductExceptionResponseCode.PRODUCT_NOT_FOUND, productId+"의 상품이 존재하지 않습니다."));

        List<StatementsHistoryResponseDTO> statementsHistoryResponseDTOS = new ArrayList<>();

        //상품 존재-> 비회원은 상품만 조회, 회원은 상품 + 거래내역도 같이조회
        if(userId != null){
            User currentUser = userRepository.findById(userId).orElseThrow(() -> new UserException(UserExceptionResponseCode.EXISTS_USER, userId+"의 유저가 존재하지 않습니다."));

            //상품의 유저와 현재 유저와 같으면(판매자)
            if(product.getUser() == currentUser){
                statementsHistoryResponseDTOS = statementsRepository.findStatementHistoriesListByProduct(product);
            }
            //같지 않으면(구매자)
            else{
                statementsHistoryResponseDTOS = statementsRepository.findStatementHistoriesList(currentUser,product);
            }
        }

        return ProductDetailResponseDTO.toDTO(product, statementsHistoryResponseDTOS);
    }


    //상품 구매
    @Transactional
    public ProductPurchaseResponseDTO purchaseProduct(ProductPurchaseRequestDTO productPurchaseRequestDTO, Long userId){
        User currentUser = userRepository.findById(userId).orElseThrow(() -> new UserException(UserExceptionResponseCode.EXISTS_USER, userId+"의 유저가 존재하지 않습니다."));

        Product product = productRepository.findById(productPurchaseRequestDTO.getId()).orElseThrow(() -> new ProductException(ProductExceptionResponseCode.PRODUCT_NOT_FOUND, productPurchaseRequestDTO.getId()+"의 상품이 존재하지 않습니다."));

        //자신의 물품 구매
        if(product.getUser() == currentUser){
            throw new ProductException(ProductExceptionResponseCode.FORBID_YOURSELF_BUYING_PRODUCT,"자신의 물품을 구매할 수 없습니다.");
        }

        //한번만 물품 구매
        if(statementsRepository.findByUserAndProduct(currentUser,product).isPresent()){
            throw new ProductException(ProductExceptionResponseCode.ALREADY_PURCHASE_PRODUCT, "이미 구매를 신청했습니다.");
        }

        //재고가 0이면 구매 할 수 없다.
        if(product.getQuantity() == 0){
            throw new ProductException(ProductExceptionResponseCode.PRODUCT_NOT_ENOUGH_QUANTITY,product.getQuantity() +"로 재고가 부족합니다.");
        }

        //재고 1개이상 이고 줄어들고 일단 내역저장먼저. 하고 감소 product
        Statements statement = Statements.builder()
                .product(product)
                .price(product.getPrice())
                .sellStatus(false)
                .purchaseStatus(false)
                .user(currentUser)
                .build();

        statementsRepository.save(statement);

        product.decreaseQuantity();

        if(product.getQuantity()==0){
            product.updateStatus();
        }

        productRepository.save(product);

        return ProductPurchaseResponseDTO.toDTO(statement);
    }


}
