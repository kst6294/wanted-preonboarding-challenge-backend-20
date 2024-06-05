package wanted.pre.onboading.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "Product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @ApiOperation(value = "모든 제품 조회", notes = "모든 제품 목록을 조회합니다.")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "제품 상세 조회", notes = "ID를 통해 제품의 상세 정보를 조회합니다.")
    public Product getProductById(@PathVariable Integer productId) {
        return productService.getProductById(productId);
    }

    @PostMapping
    @ApiOperation(value = "새 제품 등록", notes = "새로운 제품을 등록합니다.")
    public String createProduct(@ApiParam(value = "등록할 제품 정보", required = true) @RequestBody Product product, HttpSession session) {
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
    @ApiOperation(value = "제품 상태 변경", notes = "제품의 상태를 변경합니다.")
    public String updateProductStatus(@ApiParam(value = "상태를 변경할 제품의 ID", required = true) @PathVariable Integer productId,
                                      @ApiParam(value = "변경할 제품 상태", required = true) @RequestParam ProductStatus status, HttpSession session) {
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
    @ApiOperation(value = "제품 구매", notes = "제품 구매 절차를 시작합니다.")
    public String purchaseProduct(@ApiParam(value = "구매할 제품의 ID", required = true) @PathVariable Integer productId, HttpSession session) {
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

    @PostMapping("/{id}/confirm")
    @ApiOperation(value = "구매 확정", notes = "제품의 구매를 확정합니다.")
    public String confirmPurchase(@ApiParam(value = "구매를 확정할 제품의 ID", required = true) @PathVariable Integer productId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "로그인이 필요합니다.";
        }
        Product product = productService.confirmPurchase(productId, user);
        if (product == null) {
            return "구매를 확정할 수 없습니다.";
        }
        return "구매가 확정되었습니다.";
    }

    @GetMapping("/purchased")
    @ApiOperation(value = "구매한 제품 목록 조회", notes = "내가 구매한 제품 목록을 조회합니다.")
    public List<Product> getPurchasedProducts(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return productService.getProducts(user);
        }
        return null;
    }

    @GetMapping("/reversed")
    @ApiOperation(value = "예약 중인 제품 목록 조회", notes = "내가 예약중인 제품 목록을 조회합니다.")
    public List<Product> getReservedProducts(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return productService.getReservedProducts(user);
        }
        return null;
    }

    @PatchMapping("/{id}/approve")
    @ApiOperation(value = "구매 승인", notes = "구매자의 거래를 승인합니다.")
    public String approvePurchase(@ApiParam(value = "거래를 승인할 제품의 ID", required = true) @PathVariable Integer productId, HttpSession session) {
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
