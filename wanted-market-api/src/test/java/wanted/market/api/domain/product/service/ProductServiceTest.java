package wanted.market.api.domain.product.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wanted.market.api.domain.product.dto.request.RegisterProductRequestDto;
import wanted.market.api.domain.product.entity.Product;
import wanted.market.api.domain.product.repository.ProductRepository;
import wanted.market.api.domain.user.entity.User;
import wanted.market.api.domain.user.service.UserService;
import wanted.market.api.global.response.enums.ExceptionMessage;
import wanted.market.api.global.response.exception.WantedException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("Success - resisterProduct()")
    void registerSuccess() {
        RegisterProductRequestDto requestDto = RegisterProductRequestDto.builder()
                .userId(1L)
                .price(1234L)
                .count(1L)
                .productName("testProduct")
                .build();
        User user = User.builder()
                .nickname("testNickname")
                .build();
        Product product = Product.builder().build();
        when(userService.getUser(requestDto.getUserId())).thenReturn(user);


        productService.registerProduct(requestDto);

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Success - getProduct()")
    void getProductSuccess() {
        Long mockProductId = 1L;
        User user = User.builder()
                .nickname("testNickname")
                .build();
        Product product = Product.builder()
                .name("testProduct")
                .user(user)
                .build();
        when(productRepository.findById(mockProductId)).thenReturn(Optional.of(product));

        productService.searchProductDetail(mockProductId);

        verify(productRepository, times(1)).findById(mockProductId);
    }

    @Test
    @DisplayName("Failure - getProduct() / cause : productId is not exist")
    void getProductFailure() {
        Long mockProductId = 1L;
        when(productRepository.findById(mockProductId)).thenReturn(Optional.empty());

        WantedException exception = assertThrows(WantedException.class, () -> {
            productService.searchProductDetail(1L);
        });


        assertEquals(ExceptionMessage.ISNOTEXIST.getName(), exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
    }


    @Test
    @DisplayName("Success - getAllProducts()")
    void getAllProductsSuccess(){
        //TODO Notion-고민거리
    }
    @Test
    @DisplayName("Success - getAllProducts(-1) / cause : page set 0 when page is negative")
    void getAllProductsSuccessWhenNegativePage(){
        int page = -1;
        //TODO Notion-고민거리
    }

}