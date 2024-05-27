package com.backend.market.Service.Product;

import com.backend.market.DAO.Entity.Product;
import com.backend.market.DAO.Entity.PurchaseList;
import com.backend.market.DAO.Entity.Status;
import com.backend.market.Request.ProductReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getList()
    {
        return this.productRepository.findAll();
    }

    //TODO: 판매자와 구매자는 제품의 상세정보를 조회하면 당사자간의 이전 거래내역을 볼 수 있다.
    public Product getProduct(Long id)throws Exception
    {
        Optional<Product> product = this.productRepository.findById(id);
        if(product.isPresent())
        {
            Product p = product.get();
            return p;
        }else {
            throw new IllegalArgumentException("목록에 없는 제품입니다.");
        }

    }

    public Product addProduct(ProductReq productReq)
    {
        Product product = new Product();
        product.setProduct_name(productReq.getProduct_name());
        product.setStatus(Status.sale);
        product.setPrice(productReq.getPrice());
        product.setQuantity(productReq.getQuantity());
        product.setMember(productReq.getMember());

        return this.productRepository.save(product);
    }

    public boolean updateStatus(ProductReq productReq)
    {
        Optional<Product> product = productRepository.findById(productReq.getProduct_id());

        if(product.isPresent()) {
            Product newproduct = product.get();

            if(productReq.getStatus() == Status.complete){
                newproduct.setQuantity(newproduct.getQuantity() > 1 ? newproduct.getQuantity()-1 : 0);
            }

            newproduct.setStatus(productReq.getStatus());
            this.productRepository.save(newproduct);
            return true;
        }
        return false;
    }

    //판매승인이 되면 거래내역 등록
    public void addPurchasList(ProductReq productReq, Long buy_id)
    {
        PurchaseList purchaseList = new PurchaseList();
        //등록한 판매자의 아이디
        Long id = productReq.getMember().getUserId();

        purchaseList.setProduct_id(productReq.getProduct_id());
        purchaseList.setBuyer_id(buy_id);
        purchaseList.setSeller_id(id);
        purchaseList.setCreaeDate(LocalDate.now());

    }

    public List<Product> getBoughtList(Long id)
    {
        return this.productRepository.findAllCompleteById(id);
    }

    public List<Product> getReservationsList(Long id)
    {
        return this.productRepository.findAllReservationById(id);
    }
}
