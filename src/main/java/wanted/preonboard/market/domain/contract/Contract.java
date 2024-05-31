package wanted.preonboard.market.domain.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Contract {
    @JsonIgnore
    private Integer id;
    private Integer productId;
    @JsonIgnore
    private Integer sellerId;
    @JsonIgnore
    private Integer buyerId;
    private ContractState state;
}
