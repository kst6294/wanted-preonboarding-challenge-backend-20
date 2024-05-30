package wanted.preonboard.market.service.Contract;

import wanted.preonboard.market.domain.product.Product;

public interface ContractService {
    Boolean createContract(Integer buyerId, Product product);
}
