package com.wanted.preonboarding.module.order.mapper;

import com.wanted.preonboarding.data.order.OrderFactory;
import com.wanted.preonboarding.data.users.UsersModuleHelper;
import com.wanted.preonboarding.document.utils.SecuritySupportTest;
import com.wanted.preonboarding.module.common.dto.CustomSlice;
import com.wanted.preonboarding.module.common.filter.ItemFilter;
import com.wanted.preonboarding.module.order.core.DetailedOrderContext;
import com.wanted.preonboarding.module.user.entity.Users;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


@ExtendWith(MockitoExtension.class)
class OrderSliceMapperImplTest extends SecuritySupportTest {

    @InjectMocks
    private OrderSliceMapperImpl orderSliceMapper;

    private Users buyer;
    private Users seller;

    @BeforeEach
    void setUp() {
        buyer = UsersModuleHelper.toUsersWithId();
        seller = UsersModuleHelper.toUsersWithId();
        securityUserMockSetting(buyer);
        securityUserMockSetting(seller);
    }

    @Test
    @DisplayName("toSlice: 데이터를 포함한 슬라이스 반환")
    void toSlice_withData() {
        List<DetailedOrderContext> data = OrderFactory.generateDetailedOrderContext(buyer, seller,6);
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id"));
        ItemFilter filter = mock(ItemFilter.class);

        CustomSlice<DetailedOrderContext> customSlice = orderSliceMapper.toSlice(data, pageable, filter);

        assertThat(customSlice).isNotNull();
        AssertionsForInterfaceTypes.assertThat(customSlice.getContent()).hasSize(5);
        assertFalse(customSlice.isLast());
        assertThat(customSlice.getLastDomainId()).isEqualTo(data.get(5).getId());
    }

    @Test
    @DisplayName("toSlice: 빈 데이터 처리")
    void toSlice_emptyData() {
        List<DetailedOrderContext> data = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id"));
        ItemFilter filter = mock(ItemFilter.class);

        CustomSlice<DetailedOrderContext> customSlice = orderSliceMapper.toSlice(data, pageable, filter);

        assertThat(customSlice).isNotNull();
        AssertionsForInterfaceTypes.assertThat(customSlice.getContent()).isEmpty();
        assertTrue(customSlice.isLast());
        assertThat(customSlice.getLastDomainId()).isNull();
    }
}