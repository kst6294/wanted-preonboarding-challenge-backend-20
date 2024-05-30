package wanted.market.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wanted.market.entity.Product;
import wanted.market.service.ProductService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 상품목록 조회
    @GetMapping()
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // 상품상세 조회
    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable Long id) {
        try {
            Map<String, Object> result = productService.getProductById(id);

            return (result.isEmpty())? ResponseEntity.notFound().build() : ResponseEntity.ok(result);

        }catch (Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    // 상품등록
    @PostMapping("/register")
    public ResponseEntity registerProduct(@RequestBody Map<String, String> request) {
        try {
            String name = request.get("name");
            int price = Integer.parseInt(request.get("price"));
            int quantity = Integer.parseInt(request.get("quantity"));

            productService.registerProduct(name, price, quantity);
            return ResponseEntity.ok("상품 등록이 완료되었습니다.");
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    // 판매목록 조회
    @GetMapping("/my")
    public ResponseEntity getMyProducts() {
        try {
            List<Product> products = productService.getMyProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errors);
        }
    }
}
