package wanted.Market.domain.transaction.dto;

import lombok.*;
import wanted.Market.domain.transaction.entity.Transaction;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {
    private Long id;
    private String buyerName;
    private boolean status;
    private Long productId;

    // 엔티티를 DTO로 변환하는 메서드
    public static TransactionDto fromEntity(Transaction transaction) {
        return TransactionDto.builder()
            .id(transaction.getId())
            .buyerName(transaction.getBuyerName())
            .status(transaction.isStatus())
            .productId(transaction.getProduct().getId())
            .build();
    }
}