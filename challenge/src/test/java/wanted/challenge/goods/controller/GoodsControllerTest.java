package wanted.challenge.goods.controller;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import wanted.challenge.aop.api.ApiResponse;
import wanted.challenge.custom.CustomEnumDeserializer;
import wanted.challenge.custom.CustomEnumSerializer;
import wanted.challenge.goods.dto.response.GoodsResponseDto;
import wanted.challenge.goods.entity.Goods;
import wanted.challenge.goods.entity.GoodsStatus;
import wanted.challenge.goods.mapper.GoodsMapper;
import wanted.challenge.goods.service.GoodsService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(GoodsController.class)
@AutoConfigureMockMvc
class GoodsControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GoodsService goodsService;

    @MockBean
    private GoodsMapper mapper;

    List<Goods> mockGoodsList = new ArrayList<>();
    List<GoodsResponseDto.GoodsListItem> mockResponseList = new ArrayList<>();
    GoodsResponseDto.GoodsListItem goodsListItem = new GoodsResponseDto.GoodsListItem(1L, "goodsName", 1000, GoodsResponseDto.GoodsStatus.판매중, 10);
    Goods goods = new Goods("goodsName", 1000, GoodsStatus.SALE, 10);

    @BeforeEach
    public void setup() {
        objectMapper.registerModule(new JavaTimeModule());
        mockGoodsList.add(goods);
        mockResponseList.add(goodsListItem);
    }


    @Test
    void getGoodsList_Success() throws Exception {
        // given
        // when
        Mockito.when(goodsService.getGoodsList()).thenReturn(mockGoodsList);
        Mockito.when(mapper.toGoodsList(mockGoodsList)).thenReturn(mockResponseList);
        // Perform the HTTP request
        MvcResult mvcResult = mockMvc.perform(get("/goods"))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<List<GoodsResponseDto.GoodsListItem>> response = new ObjectMapper()
                .readValue(mvcResult.getResponse().getContentAsString(), ApiResponse.class);

        assertEquals(200, response.status());
        assertEquals("success", response.message());
        // 응답 데이터 검증
        List<GoodsResponseDto.GoodsListItem> actualGoodsList = response.data();
        assertNotNull(actualGoodsList); // null이 아닌지 확인

        // (선택) 응답 데이터의 개수 검증
        assertEquals(1, actualGoodsList.size());  // 예상 데이터 개수와 비교
    }

    @Test
    void getGoodsDetail_NotMember() throws Exception {
        //given
        Long goodsId = 1L;
        Long memberId = null;
        GoodsResponseDto.GoodsDetail mockGoodsDetail = new GoodsResponseDto.GoodsDetail(
                1L, "goodsName", 1000,
                GoodsResponseDto.GoodsStatus.판매중, 10, LocalDateTime.now(), new ArrayList<>());

        // when
        Mockito.when(goodsService.getGoodsDetail(goodsId, memberId)).thenReturn(mockGoodsDetail);
        // then
        // Perform the HTTP request
        MvcResult mvcResult = mockMvc.perform(get("/goods/{goods_id}", goodsId)
                        .characterEncoding("UTF-8")
                        .contentType("application/json")
                )
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<GoodsResponseDto.GoodsDetail> response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<ApiResponse<GoodsResponseDto.GoodsDetail>>() {});

        assertEquals(200, response.status());
        assertEquals("success", response.message());
        // 응답 데이터 검증
       GoodsResponseDto.GoodsDetail actualGoodsDetail= response.data();
        assertNotNull(response.data()); // null이 아닌지 확인

        // 응답 데이터의 tradeList가 비어있는지 확인
        assertTrue(actualGoodsDetail.tradeList().isEmpty());

    }


    @Test
    void getGoodsDetail_IsMemberAndNotTrade() {
    }


    @Test
    void getGoodsDetail_IsMemberAndTradeGoods() {
    }
    @Test
    void getGoodsDetail_Fail_NotFoundGoods() {
    }


    @Test
    void createGoods_Success() {
    }

    @Test
    void createGoods_Fail_NotMember() {
    }

    @Test
    void createGoods_Fail_validInfo() {
    }

    //
    @Test
    void updateGoods() {
    }

    @Test
    void deleteGoods() {
    }

    @Test
    void orderGoods() {
    }
}