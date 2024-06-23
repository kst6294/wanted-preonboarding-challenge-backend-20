package kr.co.wanted.market.trade.service;

import kr.co.wanted.market.common.global.dto.PageInfo;
import kr.co.wanted.market.common.global.dto.PageResult;
import kr.co.wanted.market.common.global.enums.ErrorCode;
import kr.co.wanted.market.common.global.error.BizException;
import kr.co.wanted.market.common.global.utils.ContextUtil;
import kr.co.wanted.market.member.entity.Member;
import kr.co.wanted.market.member.service.MemberService;
import kr.co.wanted.market.product.entity.Product;
import kr.co.wanted.market.product.repository.ProductRepository;
import kr.co.wanted.market.trade.dto.Purchase;
import kr.co.wanted.market.trade.dto.TradeHistory;
import kr.co.wanted.market.trade.dto.TradeStateModification;
import kr.co.wanted.market.trade.entity.Trade;
import kr.co.wanted.market.trade.enums.TradeState;
import kr.co.wanted.market.trade.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TradeService {

    private final MemberService memberService;

    private final ProductRepository productRepository;

    private final TradeRepository tradeRepository;


    /**
     * 구매하기 위한 거래를 생성합니다.
     *
     * @param purchase 구매정보
     * @return 생성된 거래정보
     * @throws DataIntegrityViolationException 구매자가 같은 상품에 대해 구매시 발생합니다.
     */
    @Transactional
    public Purchase purchase(Purchase purchase) throws DataIntegrityViolationException {

        // 구매자 찾기
        Member buyer = memberService.findCurrentMember()
                .orElseThrow(() -> new BizException(ErrorCode.MEMBER_NOT_FOUND));

        // 상품 찾기
        Product product = productRepository.findByIdWithSeller(purchase.productId())
                .orElseThrow(() -> new BizException(ErrorCode.PRODUCT_NOT_FOUND));

        // 이미 구매중인지 확인
        boolean exists = tradeRepository.existsByBuyerAndProductAndStateIsNot(buyer, product, TradeState.CANCEL);
        if (exists) {
            throw new BizException(ErrorCode.TRADE_ALREADY_EXISTED);
        }

        // 재고차감 및 상태변경 시도
        int changedRow = productRepository.subtractQuantityById(product.getId(), 1L);
        // 재고차감 실패시 품절
        if (changedRow == 0) {
            throw new BizException(ErrorCode.PRODUCT_SOLD_OUT);
        }

        /*
        거래 저장
        구매자가 구매하기 눌렀을 때 가격을 저장한다.
         */
        Trade trade = tradeRepository.save(new Trade(
                buyer,
                product.getSeller(),
                product,
                product.getPrice(),
                1L
        ));

        return Purchase.fromEntity(trade);
    }


    /**
     * 판매거래 목록을 조회합니다.
     *
     * @param pageInfo 페이징 정보
     * @return 판매거래 정보 목록
     */
    public PageResult<TradeHistory> searchSales(PageInfo pageInfo) {

        // 최신순
        PageRequest pageRequest = pageInfo.toPageRequest(
                Sort.by(Sort.Direction.DESC, "id")
        );

        // 페이징 조회
        Page<Trade> trades = tradeRepository.findAllBySellerIdWithProductAndBuyer(
                ContextUtil.getMemberId().orElseThrow(() -> new BizException(ErrorCode.MEMBER_NOT_FOUND)),
                pageRequest);

        return PageResult.of(trades, TradeHistory::fromEntity);
    }


    /**
     * 구매거래 목록을 조회합니다.
     *
     * @param pageInfo 페이징 정보
     * @return 판매거래 정보 목록
     */
    public PageResult<TradeHistory> searchPurchases(PageInfo pageInfo) {

        // 최신순
        PageRequest pageRequest = pageInfo.toPageRequest(
                Sort.by(Sort.Direction.DESC, "id")
        );

        // 페이징 조회
        Page<Trade> trades = tradeRepository.findAllByBuyerIdWithProductAndBuyer(
                ContextUtil.getMemberId().orElseThrow(() -> new BizException(ErrorCode.MEMBER_NOT_FOUND)),
                pageRequest);

        return PageResult.of(trades, TradeHistory::fromEntity);
    }


    /**
     * 거래 상태를 변경합니다.
     * CANCEL 이라면 한단계 이전으로 되돌립니다.
     *
     * @param id    거래 ID
     * @param state 변경할 상태
     * @return 변경된 상태
     */
    @Transactional
    public TradeStateModification changeState(Long id, TradeState state) {

        Trade trade = tradeRepository.findByIdWithBuyerAndSellerAndProduct(id)
                .orElseThrow(() -> new BizException(ErrorCode.TRADE_NOT_FOUND));

        switch (state) {
            // 판매자가 판매승인(예약)
            case BOOKING -> trade.approveSelling();

            // 구매자가 구매확정
            case CONFIRMATION -> {
                trade.confirm();

                // 구매확정시 상품의 상태가 바뀔 수 있다.
                int changedRow = productRepository.updateStateAfterTradeConfirmation(trade.getProduct().getId());
                if (changedRow == 0) {
                    throw new BizException(ErrorCode.TRADE_STATE_CAN_NOT_CHANGED);
                }
            }

            // 취소(상태 되돌리기)
            // 판매자인 경우 'BOOKING' -> 'REQUEST' : 판매승인 이전으로 돌아감
            // 구매자인 경우 'REQUEST' -> 'CANCEL'  : 구매거래 취소
            case CANCEL -> {
                trade.revertState();

                // 구매거래 취소 외
                if (trade.getState() != TradeState.CANCEL) {
                    break;
                }

                // 구매거래 취소의 경우 재고를 다시 증가시켜야 한다.
                int changedRow = productRepository.plusQuantityById(trade.getProduct().getId(), trade.getQuantity());
                if (changedRow == 0) {
                    throw new BizException(ErrorCode.TRADE_STATE_CAN_NOT_CHANGED);
                }

            }

            default -> throw new BizException(ErrorCode.TRADE_STATE_CAN_NOT_CHANGED);
        }

        // 변경된 상태 응답
        return new TradeStateModification(trade.getId(), trade.getState());
    }
}
