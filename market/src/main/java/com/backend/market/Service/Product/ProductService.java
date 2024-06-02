package com.backend.market.Service.Product;

import com.backend.market.Common.auth.UserDetailsImpl;
import com.backend.market.DAO.Entity.Member;
import com.backend.market.DAO.Entity.Product;
import com.backend.market.DAO.Entity.PurchaseList;
import com.backend.market.DAO.Entity.Status;
import com.backend.market.Repository.MemberRepository;
import com.backend.market.Repository.ProductRepository;
import com.backend.market.Request.ProductReq;
import com.backend.market.Service.purchaseList.PurchaseListService;
import lombok.RequiredArgsConstructor;
import org.hibernate.metamodel.internal.MemberResolver;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final PurchaseListService purchaseListService;

    public List<Product> getList()
    {
        return this.productRepository.findAll();
    }

    public Product getProduct(Long id,UserDetailsImpl userDetails)throws Exception
    {
        Member member = isExistUser(userDetails);

        Optional<Product> product = this.productRepository.findById(id);
        if(product.isPresent())
        {
            Product p = product.get();
            return p;
        }else {
            throw new IllegalArgumentException("목록에 없는 제품입니다.");
        }

    }

    public Product addProduct(ProductReq productReq, UserDetailsImpl userDetails)
    {
        Product product = new Product();
        Member member = isExistUser(userDetails);
        if(member == null ) return null;
        product.setProduct_name(productReq.getProduct_name());
        product.setStatus(Status.sale);
        product.setPrice(productReq.getPrice());
        product.setQuantity(productReq.getQuantity());
        product.setMember(member);
        product.setCreaeDate(LocalDate.now());

        return this.productRepository.save(product);
    }

    public boolean updateStatus(ProductReq productReq, UserDetailsImpl userDetails)
    {
        Member member = isExistUser(userDetails);
        if(member == null) return false;

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

    //판매승인
    public void permitSaleForProduct(ProductReq productReq, UserDetailsImpl userDetails)
    {
        //제품 상태 변경 : 예약 -> 완료
        productReq.setStatus(Status.complete);
        updateStatus(productReq,userDetails);

        //거래내역 상태 변경
        purchaseListService.updateOrderStatus(productReq);
    }


    public List<Product> getBoughtList(Long id, UserDetailsImpl userDetails)
    {
        Member member = isExistUser(userDetails);
        if(member == null) return null;

        return this.productRepository.findAllCompleteById(id);
    }

    public List<Product> getReservationsList(Long id , UserDetailsImpl userDetails)
    {
        Member member = isExistUser(userDetails);
        if(member == null) return null;
        return this.productRepository.findAllReservationById(id);
    }

    private Member isExistUser(UserDetailsImpl userDetails)
    {
        Long userId = userDetails.getUser().getUserId();
        Optional<Member> findMember = memberRepository.findById(userId);
        return findMember.orElse(null);
    }
}
