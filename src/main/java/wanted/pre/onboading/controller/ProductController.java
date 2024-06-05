package wanted.pre.onboading.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wanted.pre.onboading.entity.Product;
import wanted.pre.onboading.entity.ProductStatus;
import wanted.pre.onboading.entity.User;
import wanted.pre.onboading.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Integer productId) {
        return productService.getProductById(productId);
    }

    @PostMapping
    public String createProduct(@RequestBody Product product, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (session.getAttribute("user") == null) {
            return "로그인이 필요합니다.";
        } else {
            product.setStatus(ProductStatus.판매중);
            product.setSeller(user);
            productService.saveProduct(product);
            return "제품이 등록되었습니다.";
        }
    }

    @PatchMapping("/{id}/status")
    public String updateProductStatus(@PathVariable Integer productId, @RequestParam ProductStatus status, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (session.getAttribute("user") == null) {
            return "로그인이 필요합니다.";
        }
        Product product = productService.updateProductStatus(productId, status);
        if (product == null) {
            return "제품을 찾을 수 없습니다.";
        }
        return "제품 상태가 변경되었습니다.";
    }

    @PostMapping("/{id}/purchase")
    public String purchaseProduct(@PathVariable Integer productId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "로그인이 필요합니다.";
        }
        Product product = productService.purchaseProduct(productId, user);
        if (product == null) {
            return "제품을 구매할 수 없습니다.";
        }
        return "구매가 완료되었습니다.";
    }

    @GetMapping("/purchased")
    public List<Product> getPurchasedProducts(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return productService.getProducts(user);
        }
        return null;
    }

    @GetMapping("/reversed")
    public List<Product> getReservedProducts(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return productService.getReservedProducts(user);
        }
        return null;
    }

    @PatchMapping("/{id}/approve")
    public String approvePurchase(@PathVariable Integer productId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "로그인이 필요합니다.";
        }
        Product product = productService.approvePurchase(productId, user);
        if (product == null) {
            return "거래 불가";
        }
        return "거래가 완료되었습니다.";
    }
}
