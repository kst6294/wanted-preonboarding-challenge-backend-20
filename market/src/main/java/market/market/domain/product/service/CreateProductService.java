package market.market.domain.product.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import market.market.domain.product.domain.Product;
import market.market.domain.product.domain.repository.ProductRepository;
import market.market.domain.product.enums.Status;
import market.market.domain.product.presentation.dto.requset.CreateProduct;
import market.market.domain.user.domain.User;
import market.market.domain.user.facade.UserFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProductService {
    private final UserFacade userFacade;
    private final ProductRepository productRepository;

    @Transactional
    public void execute(CreateProduct createProduct) {
        User user = userFacade.getCurrentUser();

        /*
        productRepository.save(
                Product.builder()
                        .user(user)
                        .name(createProduct.getName())
                        .price(createProduct.getPrice())
                        .status(Status.Sale)
                        .build()
        );
        */

        // 2단계 물품 수량 추가
        productRepository.save(
                Product.builder()
                        .user(user)
                        .name(createProduct.getName())
                        .price(createProduct.getPrice())
                        .status(Status.Sale)
                        .quantity(createProduct.getQuantity())
                        .build()
        );
    }
}
