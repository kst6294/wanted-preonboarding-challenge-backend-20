package wanted.pre.onboading.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wanted.pre.onboading.entity.Product;
import wanted.pre.onboading.entity.ProductStatus;
import wanted.pre.onboading.entity.Purchase;
import wanted.pre.onboading.entity.User;
import wanted.pre.onboading.repository.ProductRepository;
import wanted.pre.onboading.repository.PurchaseRepository;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Integer productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProductStatus(Integer productId, ProductStatus status) {
        Product product = getProductById(productId);
        if (product != null) {
            product.setStatus(status);
            return productRepository.save(product);
        }
        return null;
    }

    public Product purchaseProduct(Integer productId, User buyer){
        Product product = getProductById(productId);
        if (product != null && product.getStatus() == ProductStatus.판매중 && product.getQuantity() > 0) {
            Purchase purchase = new Purchase();
            purchase.setProduct(product);
            purchase.setBuyer(buyer);
            purchase.setPurchasePrice(product.getPrice());
            purchase.setConfirmed(false);
            purchaseRepository.save(purchase);

            product.getPurchases().add(purchase);
            product.setQuantity(product.getQuantity() - 1);

            if (product.getQuantity() == 0) {
                product.setStatus(ProductStatus.예약중);
            }
            return productRepository.save(product);
        }
        return null;
    }

    public Product confirmPurchase(Integer productId, User buyer) {
        Purchase purchase = purchaseRepository.findByProductIdAndBuyerId(productId, buyer.getUserId());
        if (purchase != null && !purchase.isConfirmed()) {
            purchase.setConfirmed(true);
            purchaseRepository.save(purchase);

            Product product = purchase.getProduct();
            boolean allConfirmed = product.getPurchases().stream().allMatch(Purchase::isConfirmed);
            if (allConfirmed) {
                product.setStatus(ProductStatus.완료);
                return productRepository.save(product);
            }
        }
        return null;
    }

    public Product approvePurchase(Integer productId, User seller) {
        Product product = getProductById(productId);
        if (product != null && product.getSeller().equals(seller) && product.getStatus() == ProductStatus.예약중) {
            product.getPurchases().forEach(purchase -> {
                if (!purchase.isConfirmed()) {
                    purchase.setConfirmed(true);
                    purchaseRepository.save(purchase);
                }
            });
        }
        return null;
    }

    public List<Product> getProducts(User buyer) {
        return productRepository.findByPurchasesBuyer(buyer);
    }

    public List<Product> getReservedProducts(User user) {
        return productRepository.findByUser(user, user, ProductStatus.예약중);
    }

}
