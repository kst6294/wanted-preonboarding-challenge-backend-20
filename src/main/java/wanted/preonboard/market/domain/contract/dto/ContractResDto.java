package wanted.preonboard.market.domain.contract.dto;

import lombok.Getter;
import lombok.Setter;
import wanted.preonboard.market.domain.contract.Contract;

@Getter
@Setter
public class ContractResDto extends Contract {
    private String sellerName;
    private String buyerName;
}
