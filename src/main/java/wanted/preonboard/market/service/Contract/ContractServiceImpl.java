package wanted.preonboard.market.service.Contract;

import org.springframework.stereotype.Service;
import wanted.preonboard.market.domain.contract.Contract;
import wanted.preonboard.market.domain.contract.dto.ContractResDto;
import wanted.preonboard.market.domain.product.Product;
import wanted.preonboard.market.mapper.ContractMapper;

import java.util.List;

@Service
public class ContractServiceImpl implements ContractService {
    private final ContractMapper contractMapper;

    public ContractServiceImpl(ContractMapper contractMapper) {
        this.contractMapper = contractMapper;
    }

    /**
     * @param buyerId 구매자 ID
     * @param product Product
     * @return Boolean
     */
    @Override
    public Boolean createContract(Integer buyerId, Product product) {
        Contract contract = new Contract();
        contract.setProductId(product.getId());
        contract.setSellerId(product.getSellerId());
        contract.setBuyerId(buyerId);
        return contractMapper.createContract(contract) == 1;
    }

    /**
     * @param productId 제품 ID
     * @return List<ContractResDto>
     */
    @Override
    public List<ContractResDto> getContractsByProductId(Integer productId) {
        return contractMapper.selectContractsByProductId(productId);
    }

    /**
     * @param userId1 사용자1 ID
     * @param userId2 사용자2 ID
     * @return List<ContractResDto>
     */
    @Override
    public List<ContractResDto> getContractsByUserIds(Integer userId1, Integer userId2) {
        return contractMapper.selectContractsByUserIds(userId1, userId2);
    }

    /**
     * @param userId 사용자 ID
     * @return List<ContractResDto>
     */
    @Override
    public List<Product> getPurchasedContractsByUserId(Integer userId) {
        return contractMapper.selectPurchasedContractsByUserId(userId);
    }

    /**
     * @param userId 사용자 ID
     * @return List<ContractResDto>
     */
    @Override
    public List<Product> getReservationContractsByUserId(Integer userId) {
        return contractMapper.selectReservationContractsByUserId(userId);
    }

    /**
     * @param contractId 거래 ID
     * @return Contract
     */
    @Override
    public Contract getContractById(Integer contractId) {
        return contractMapper.selectContractById(contractId);
    }

    /**
     * @param contract 거래
     * @return boolean
     */
    @Override
    public boolean approveContract(Contract contract) {
        return contractMapper.approveContract(contract) == 1;
    }
}
