package wanted.preonboard.market.mapper;

import org.apache.ibatis.annotations.Mapper;
import wanted.preonboard.market.domain.contract.Contract;
import wanted.preonboard.market.domain.contract.dto.ContractResDto;
import wanted.preonboard.market.domain.product.Product;

import java.util.List;

@Mapper
public interface ContractMapper {
    int createContract(Contract contract);

    List<ContractResDto> selectContractsByProductId(Integer productId);

    List<ContractResDto> selectContractsByUserIds(Integer userId1, Integer userId2);

    List<Product> selectPurchasedContractsByUserId(Integer userId);

    List<Product> selectReservationContractsByUserId(Integer userId);

    Contract selectContractById(Integer contractId);

    int approveContract(Contract contract);
}
