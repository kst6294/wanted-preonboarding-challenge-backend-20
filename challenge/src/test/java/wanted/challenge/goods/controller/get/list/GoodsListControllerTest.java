package wanted.challenge.goods.controller.get.list;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MvcResult;
import wanted.challenge.aop.api.ApiResponse;
import wanted.challenge.goods.controller.GoodsControllerTest;
import wanted.challenge.goods.dto.response.GoodsResponseDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class GoodsListControllerTest extends GoodsControllerTest {

    @Test
    @DisplayName("상품 목록 조회 성공")
    void getGoodsList_Success() throws Exception {
        // given
        // when
        Mockito.when(goodsService.getGoodsList()).thenReturn(mockGoodsList);
        Mockito.when(mapper.toGoodsList(mockGoodsList)).thenReturn(mockResponseList);
        // Perform the HTTP request
        MvcResult mvcResult = mockMvc.perform(get("/goods"))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<List<GoodsResponseDto.GoodsListItem>> response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<ApiResponse<List<GoodsResponseDto.GoodsListItem>>>() {
                });

        assertEquals(200, response.status());
        assertEquals("success", response.message());
        // 응답 데이터 검증
        List<GoodsResponseDto.GoodsListItem> actualGoodsList = response.data();
        assertNotNull(actualGoodsList); // null이 아닌지 확인

        // (선택) 응답 데이터의 개수 검증
        assertEquals(1, actualGoodsList.size());  // 예상 데이터 개수와 비교
    }
}