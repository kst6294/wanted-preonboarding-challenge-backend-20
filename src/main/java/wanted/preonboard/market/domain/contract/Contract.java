package wanted.preonboard.market.domain.contract;

import lombok.Data;

@Data
public class Contract {
    private Integer id;
    private Integer productId;
    private Integer sellerId;
    private Integer buyerId;
    private ContractState state;
}
