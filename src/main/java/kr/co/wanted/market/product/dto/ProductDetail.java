package kr.co.wanted.market.product.dto;

import kr.co.wanted.market.product.entity.Product;
import kr.co.wanted.market.product.enums.ProductState;
import kr.co.wanted.market.trade.dto.TradeHistory;

import java.util.List;

public record ProductDetail(Long id,
                            Long sellerId,
                            String name,
                            Long price,
                            Long quantity,
                            ProductState state,
                            List<TradeHistory> tradeHistories) {

    public static ProductDetail fromEntity(Product product) {

        return new ProductDetail(
                product.getId(),
                product.getSeller().getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getState(),
                null);
    }

    public static ProductDetail of(Product product, List<TradeHistory> tradeHistories) {

        return new ProductDetail(
                product.getId(),
                product.getSeller().getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getState(),
                tradeHistories);
    }
}
