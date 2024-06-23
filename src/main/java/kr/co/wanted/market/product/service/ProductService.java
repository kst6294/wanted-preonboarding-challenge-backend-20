package kr.co.wanted.market.product.service;

import kr.co.wanted.market.common.global.dto.PageInfo;
import kr.co.wanted.market.common.global.dto.PageResult;
import kr.co.wanted.market.common.global.enums.ErrorCode;
import kr.co.wanted.market.common.global.error.BizException;
import kr.co.wanted.market.common.global.utils.ContextUtil;
import kr.co.wanted.market.member.entity.Member;
import kr.co.wanted.market.member.service.MemberService;
import kr.co.wanted.market.product.dto.ProductDetail;
import kr.co.wanted.market.product.dto.ProductRegistration;
import kr.co.wanted.market.product.dto.ProductSimple;
import kr.co.wanted.market.product.entity.Product;
import kr.co.wanted.market.product.repository.ProductRepository;
import kr.co.wanted.market.trade.dto.TradeHistory;
import kr.co.wanted.market.trade.entity.Trade;
import kr.co.wanted.market.trade.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final MemberService memberService;

    private final ProductRepository productRepository;

    private final TradeRepository tradeRepository;

    /**
     * 상품을 등록합니다.
     *
     * @param productRegistration 등록 정보
     * @return 등록된 정보
     */
    @Transactional
    public ProductRegistration registerProduct(ProductRegistration productRegistration) {

        // 요청 회원 찾기
        Member member = memberService.findCurrentMember()
                .orElseThrow(() -> new BizException(ErrorCode.MEMBER_NOT_FOUND));

        // 상품 Entity 생성후 저장
        Product product = productRepository.save(
                new Product(
                        member,
                        productRegistration.name(),
                        productRegistration.price(),
                        productRegistration.quantity()
                )
        );

        // 등록된 정보 생성후 응답
        return ProductRegistration.fromEntity(product);
    }


    /**
     * 상품 상세를 조회합니다.
     *
     * @param seq 상품번호
     * @return 상품 상세
     */
    public ProductDetail searchProductDetail(Long seq) {

        Product product = productRepository.findByIdWithSeller(seq)
                .orElseThrow(() -> new BizException(ErrorCode.PRODUCT_NOT_FOUND));

        // 비로그인 유저는 상품정보만
        Optional<Long> optionalMemberId = ContextUtil.getMemberId();
        if (optionalMemberId.isEmpty()) {
            return ProductDetail.fromEntity(product);
        }

        Long memberId = optionalMemberId.get();

        // 조회자가 판매자 - 해당 상품의 판매거래 목록을 동시에 반환
        if (Objects.equals(product.getSeller().getId(), memberId)) {
            List<Trade> trades = tradeRepository.findAllBySellerIdAndProductId(memberId, product.getId());
            return ProductDetail.of(
                    product,
                    trades.stream()
                            .map(TradeHistory::fromEntity)
                            .toList());
        }

        // 조회자가 구매자 - 해당 상품의 구매거래 목록을 동시에 반환
        List<Trade> trades = tradeRepository.findAllByBuyerIdAndProductId(memberId, product.getId());
        return ProductDetail.of(
                product,
                trades.stream()
                        .map(TradeHistory::fromEntity)
                        .toList());
    }


    /**
     * 상품 정보 목록을 조회합니다.
     *
     * @param pageInfo 페이징 정보
     * @return 상품 정보 목록
     */
    public PageResult<ProductSimple> searchProducts(PageInfo pageInfo) {

        // 최신순
        PageRequest pageRequest = pageInfo.toPageRequest(
                Sort.by(Sort.Direction.DESC, "id")
        );

        // 페이징 조회
        Page<Product> products = productRepository.findAll(pageRequest);

        // DTO 변환
        return PageResult.of(products, ProductSimple::fromEntity);
    }
}
