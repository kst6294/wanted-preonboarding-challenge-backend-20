package wanted.preonboard.market.service.Contract;

import org.springframework.stereotype.Service;
import wanted.preonboard.market.domain.contract.Contract;
import wanted.preonboard.market.domain.product.Product;
import wanted.preonboard.market.mapper.ContractMapper;

@Service
public class ContractServiceImpl implements ContractService {
    private final ContractMapper contractMapper;

    public ContractServiceImpl(ContractMapper contractMapper) {
        this.contractMapper = contractMapper;
    }

    @Override
    public Boolean createContract(Integer buyerId, Product product) {
        Contract contract = new Contract();
        contract.setProductId(product.getId());
        contract.setSellerId(product.getSellerId());
        contract.setBuyerId(buyerId);
        return contractMapper.createContract(contract) == 1;
    }
}
