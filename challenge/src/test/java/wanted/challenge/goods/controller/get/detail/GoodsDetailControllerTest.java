package wanted.challenge.goods.controller.get.detail;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MvcResult;
import wanted.challenge.aop.api.ApiResponse;
import wanted.challenge.exception.GlobalExceptionHandler;
import wanted.challenge.exception.custom.GoodsNotFoundException;
import wanted.challenge.goods.controller.GoodsControllerTest;
import wanted.challenge.goods.dto.response.GoodsResponseDto;
import wanted.challenge.mypage.dto.response.MyPageResponseDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Import(GlobalExceptionHandler.class)  // 전역 예외 처리기 포함
class GoodsDetailControllerTest extends GoodsControllerTest {

    private GoodsResponseDto.GoodsDetail mockGoodsDetail;
    private MyPageResponseDto.Trade trade;

    //거래내역
    @BeforeEach
    void init() {
        mockGoodsDetail = new GoodsResponseDto.GoodsDetail(
                1L, "goodsName", 1000,
                GoodsResponseDto.GoodsStatus.판매중, 10, LocalDateTime.now(), new ArrayList<>());

        trade = new MyPageResponseDto.Trade(1L, LocalDateTime.now(), "판매승인", 1000, 1);
    }


    @Test
    @DisplayName("거래내역 있는 회원 상품 조회 성공")
    void success_IsMemberAndTradeGoods() throws Exception {
        //given
        Long goodsId = 1L;
        Long memberId = 1L;
        List<MyPageResponseDto.Trade> tradeList = new ArrayList<>();
        tradeList.add(trade);
        mockGoodsDetail.tradeList().add(trade);
        // when
        Mockito.when(goodsService.getGoodsDetail(goodsId, memberId)).thenReturn(mockGoodsDetail);
        // then
        // Perform the HTTP request
        MvcResult mvcResult = mockMvc.perform(get("/goods/{goods_id}", goodsId)
                        .header("memberId", memberId.toString())
                        .characterEncoding("UTF-8")
                        .contentType("application/json")
                )
                .andReturn();

        ApiResponse<GoodsResponseDto.GoodsDetail> response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<ApiResponse<GoodsResponseDto.GoodsDetail>>() {
                });

        // 응답 상태코드 확인
        assertEquals(200, response.status());
        assertEquals("success", response.message());
        // 응답 데이터 검증
        GoodsResponseDto.GoodsDetail actualGoodsDetail = response.data();
        assertNotNull(response.data()); // null이 아닌지 확인

        // 응답 데이터의 tradeList가 값이 있는지 확인
        assertFalse(actualGoodsDetail.tradeList().isEmpty());
    }

    @Test
    // 테스트 이름 비회원 상품 조회 성공
    @DisplayName("비회원 상품 조회 성공")
    void success_NotMember() throws Exception {
        //given
        Long goodsId = 1L;
        Long memberId = null;
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
                mvcResult.getResponse().getContentAsString(), new TypeReference<ApiResponse<GoodsResponseDto.GoodsDetail>>() {
                });

        assertEquals(200, response.status());
        assertEquals("success", response.message());
        // 응답 데이터 검증
        GoodsResponseDto.GoodsDetail actualGoodsDetail = response.data();
        assertNotNull(response.data()); // null이 아닌지 확인

        // 응답 데이터의 tradeList가 비어있는지 확인
        assertTrue(actualGoodsDetail.tradeList().isEmpty());
    }

    @Test
    @DisplayName("거래내역 없는 회원 상품 조회 성공")
    void success_IsMemberAndNotTrade() throws Exception {
        //given
        Long goodsId = 1L;
        Long memberId = 1L;

        // when
        Mockito.when(goodsService.getGoodsDetail(goodsId, memberId)).thenReturn(mockGoodsDetail);
        // then
        // Perform the HTTP request
        MvcResult mvcResult = mockMvc.perform(get("/goods/{goods_id}", goodsId)
                        .header("memberId", memberId.toString())
                        .characterEncoding("UTF-8")
                        .contentType("application/json")
                )
                .andReturn();
        ApiResponse<GoodsResponseDto.GoodsDetail> response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<ApiResponse<GoodsResponseDto.GoodsDetail>>() {
                });

        assertEquals(200, response.status());
        assertEquals("success", response.message());
        // 응답 데이터 검증
        GoodsResponseDto.GoodsDetail actualGoodsDetail = response.data();
        assertNotNull(response.data()); // null이 아닌지 확인

        // 응답 데이터의 tradeList가 비어있는지 확인
        assertTrue(actualGoodsDetail.tradeList().isEmpty());
    }


    @Test
    @DisplayName("상품이 존재하지 않을 때")
    void fail_NotFoundGoods() {
        // given
        Long goodsId = 9999L; // 없는 상품
        Long memberId = 1L;

        // when
        Mockito.when(goodsService.getGoodsDetail(goodsId, memberId)).thenThrow(new GoodsNotFoundException("찾을 수 없는 상품 ID")); // 핸들링 필요

        // then
        assertThrows(GoodsNotFoundException.class, () -> goodsService.getGoodsDetail(goodsId, memberId));
    }

    @Test
    @DisplayName("상품 ID가 유효하지 않을 때")
    void fail_InvalidGoodsId() throws Exception {
        // given
        Long goodsId = -1L; // invalid goodsId
        Long memberId = 1L;

        // when
        Mockito.when(goodsService.getGoodsDetail(goodsId, memberId)).thenThrow(new IllegalArgumentException("유효하지않은 상품 ID")); // 핸들링 필요

        // then
        MvcResult mvcResult = mockMvc.perform(get("/goods/{goods_id}", goodsId)
                        .header("memberId", memberId.toString())
                        .characterEncoding("UTF-8")
                        .contentType("application/json"))
                .andReturn();

        // 상태코드 400 확인
        assertEquals(400, mvcResult.getResponse().getStatus());
        // 예외 메시지 확인
        assertEquals("유효하지않은 상품 ID", Objects.requireNonNull(mvcResult.getResolvedException()).getMessage());

    }

    @Test
    @DisplayName("서비스 호출 실패")
    void fail_ServiceError() throws Exception {
        // given
        Long goodsId = 1L;
        Long memberId = 1L;

        // when
        Mockito.when(goodsService.getGoodsDetail(goodsId, memberId)).thenThrow(new RuntimeException("Service error")); // 핸들링 필요성을 느낌


        // then
        MvcResult mvcResult = mockMvc.perform(get("/goods/{goods_id}", goodsId)
                        .header("memberId", memberId.toString())
                        .characterEncoding("UTF-8")
                        .contentType("application/json"))
                .andReturn();

        // 상태코드 500 확인
        assertEquals(500, mvcResult.getResponse().getStatus());
        // 예외 메시지 확인
        assertEquals("Service error", Objects.requireNonNull(mvcResult.getResolvedException()).getMessage());
    }


}

