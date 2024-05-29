package wanted.preonboard.market.service.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openapitools.jackson.nullable.JsonNullable;
import wanted.preonboard.market.domain.common.ProductState;
import wanted.preonboard.market.domain.dto.ProductInsertDto;
import wanted.preonboard.market.domain.dto.ProductUpdateDto;
import wanted.preonboard.market.domain.entity.Product;
import wanted.preonboard.market.mapper.ProductMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct() {
        ProductInsertDto productInsertDto = new ProductInsertDto();
        productInsertDto.setName("Test Product");
        productInsertDto.setPrice(100L);

        when(productMapper.insertProduct(any(Product.class))).thenReturn(1);

        Boolean result = productService.createProduct(1L, productInsertDto);

        assertTrue(result);
        verify(productMapper, times(1)).insertProduct(any(Product.class));
    }

    @Test
    void getProducts() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        when(productMapper.getProducts()).thenReturn(Collections.singletonList(product));

        List<Product> products = productService.getProducts();

        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals("Test Product", products.get(0).getName());
        verify(productMapper, times(1)).getProducts();
    }

    @Test
    void getProductById() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        when(productMapper.getProductById(1L)).thenReturn(product);

        Product foundProduct = productService.getProductById(1L);

        assertNotNull(foundProduct);
        assertEquals("Test Product", foundProduct.getName());
        verify(productMapper, times(1)).getProductById(1L);
    }

    @Test
    void updateProductById() {
        ProductUpdateDto productUpdateDto = new ProductUpdateDto();
        productUpdateDto.setName(JsonNullable.of("Updated Product"));
        productUpdateDto.setPrice(JsonNullable.of(200D));
        productUpdateDto.setState(JsonNullable.of(ProductState.valueOf("RESERVED")));

        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setName("Old Product");
        existingProduct.setPrice(100L);
        existingProduct.setState(ProductState.valueOf("ON_SALE"));

        when(productMapper.getProductById(1L)).thenReturn(existingProduct);
        when(productMapper.updateProductById(any(Product.class))).thenReturn(1);

        Boolean result = productService.updateProductById(1L, productUpdateDto);

        assertTrue(result);
        assertEquals("Updated Product", existingProduct.getName());
        assertEquals(200L, existingProduct.getPrice());
        assertEquals(ProductState.RESERVED, existingProduct.getState());
        verify(productMapper, times(1)).getProductById(1L);
        verify(productMapper, times(1)).updateProductById(any(Product.class));
    }
}
