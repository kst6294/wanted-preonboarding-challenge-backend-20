package wanted.preonboard.market.mapper;

import org.apache.ibatis.annotations.Mapper;
import wanted.preonboard.market.domain.contract.Contract;

@Mapper
public interface ContractMapper {
    int createContract(Contract contract);
}
