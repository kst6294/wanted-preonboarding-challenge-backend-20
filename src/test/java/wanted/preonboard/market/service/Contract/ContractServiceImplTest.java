package wanted.preonboard.market.service.Contract;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import wanted.preonboard.market.domain.contract.Contract;
import wanted.preonboard.market.domain.contract.dto.ContractResDto;
import wanted.preonboard.market.domain.product.Product;
import wanted.preonboard.market.mapper.ContractMapper;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class ContractServiceImplTest {

    @Mock
    private ContractMapper contractMapper;

    @InjectMocks
    private ContractServiceImpl contractService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createContract() {
        Product product = new Product();
        product.setId(1);
        product.setSellerId(2);

        Contract contract = new Contract();
        contract.setProductId(1);
        contract.setSellerId(2);
        contract.setBuyerId(3);

        when(contractMapper.createContract(any(Contract.class))).thenReturn(1);

        Boolean result = contractService.createContract(3, product);

        assertTrue(result);
        verify(contractMapper, times(1)).createContract(any(Contract.class));
    }

    @Test
    void getContractsByProductId() {
        ContractResDto contractResDto = new ContractResDto();
        when(contractMapper.selectContractsByProductId(anyInt())).thenReturn(Collections.singletonList(contractResDto));

        List<ContractResDto> result = contractService.getContractsByProductId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(contractMapper, times(1)).selectContractsByProductId(anyInt());
    }

    @Test
    void getContractsByUserIds() {
        ContractResDto contractResDto = new ContractResDto();
        when(contractMapper.selectContractsByUserIds(anyInt(), anyInt())).thenReturn(Collections.singletonList(contractResDto));

        List<ContractResDto> result = contractService.getContractsByUserIds(1, 2);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(contractMapper, times(1)).selectContractsByUserIds(anyInt(), anyInt());
    }

    @Test
    void getPurchasedContractsByUserId() {
        Product product = new Product();
        when(contractMapper.selectPurchasedContractsByUserId(anyInt())).thenReturn(Collections.singletonList(product));

        List<Product> result = contractService.getPurchasedContractsByUserId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(contractMapper, times(1)).selectPurchasedContractsByUserId(anyInt());
    }

    @Test
    void getReservationContractsByUserId() {
        Product product = new Product();
        when(contractMapper.selectReservationContractsByUserId(anyInt())).thenReturn(Collections.singletonList(product));

        List<Product> result = contractService.getReservationContractsByUserId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(contractMapper, times(1)).selectReservationContractsByUserId(anyInt());
    }

    @Test
    void getContractById() {
        Contract contract = new Contract();
        when(contractMapper.selectContractById(anyInt())).thenReturn(contract);

        Contract result = contractService.getContractById(1);

        assertNotNull(result);
        verify(contractMapper, times(1)).selectContractById(anyInt());
    }

    @Test
    void approveContract() {
        Contract contract = new Contract();
        when(contractMapper.approveContract(any(Contract.class))).thenReturn(1);

        boolean result = contractService.approveContract(contract);

        assertTrue(result);
        verify(contractMapper, times(1)).approveContract(any(Contract.class));
    }
}
