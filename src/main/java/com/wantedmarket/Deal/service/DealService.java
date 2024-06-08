package com.wantedmarket.Deal.service;

import com.wantedmarket.Deal.domain.Deal;
import com.wantedmarket.Deal.exception.DealException;
import com.wantedmarket.Deal.repository.DealRepository;
import com.wantedmarket.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.wantedmarket.Deal.exception.DealErrorCode.INVALID_DEAL;
import static com.wantedmarket.Item.type.ItemStatus.RESERVED;

@Service
@RequiredArgsConstructor
public class DealService {

    private final DealRepository dealRepository;

    public List<Deal> findAllByBuyer(String username) {
        return dealRepository.findAllByBuyerUsername(username);
    }

    public List<Deal> findAllIsReserved(String username) {
        return dealRepository.findAllByItemItemStatusAndBuyerUsernameOrSellerUsername(RESERVED, username, username);
    }

    public Deal findByItemId(Long id) {
        return dealRepository.findByItemId(id)
                .orElseThrow(() -> new DealException(INVALID_DEAL));
    }

    public Deal save(Deal deal) {
        return dealRepository.save(deal);
    }

    public List<Deal> findByBuyerAndSellerOrSellerAndBuyer(Member buyer, Member seller) {
        return dealRepository.findByBuyerAndSellerOrSellerAndBuyer(buyer, seller, buyer, seller);
    }

    public Deal findById(Long id) {
        return dealRepository.findById(id)
                .orElseThrow(() -> new DealException(INVALID_DEAL));
    }
}
