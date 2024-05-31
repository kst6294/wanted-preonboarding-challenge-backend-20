package wanted.preonboard.market.service.Contract;

import wanted.preonboard.market.domain.contract.Contract;
import wanted.preonboard.market.domain.contract.dto.ContractResDto;
import wanted.preonboard.market.domain.product.Product;

import java.util.List;

public interface ContractService {
    Boolean createContract(Integer buyerId, Product product);

    List<ContractResDto> getContractsByProductId(Integer productId);

    List<ContractResDto> getContractsByUserIds(Integer userId1, Integer userId2);

    List<Product> getPurchasedContractsByUserId(Integer userId);

    List<Product> getReservationContractsByUserId(Integer userId);

    Contract getContractById(Integer contractId);

    boolean approveContract(Contract contract);
}
