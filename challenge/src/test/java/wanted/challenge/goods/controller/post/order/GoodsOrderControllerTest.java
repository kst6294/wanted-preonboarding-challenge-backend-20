package wanted.challenge.goods.controller.post.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import wanted.challenge.goods.controller.GoodsControllerTest;
import wanted.challenge.goods.dto.request.GoodsRequestDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


class GoodsOrderControllerTest extends GoodsControllerTest {

    @Test
    @DisplayName("상품 주문 성공")
    void success_orderGoods() throws Exception {
        //given
        Long goodsId = 1L;
        Long memberId = 1L;
        GoodsRequestDto.OrderGoods orderGoods = new GoodsRequestDto.OrderGoods(1);
        // when
        Mockito.when(goodsService.orderGoods(memberId, goodsId, orderGoods.quantity())).thenReturn(1L);
        // then
        MvcResult mvcResult = mockMvc.perform(post("/goods/{goods_id}/order", goodsId)
                        .header("memberId", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderGoods)))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

    }

    @Test
    @DisplayName("비회원 주문 실패")
    void fail_not_member() throws Exception {
        //given
        Long goodsId = 1L;
        GoodsRequestDto.OrderGoods orderGoods = new GoodsRequestDto.OrderGoods(1);
        // when
        // memberID가 없는 경우
        // then
        MvcResult mvcResult = mockMvc.perform(post("/goods/{goods_id}/order", goodsId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderGoods)))
                .andReturn();
        // 응답 코드 401 확인
        assertEquals(401, mvcResult.getResponse().getStatus());

    }

    @Test
    @DisplayName("상품 없음 주문 실패")
    void orderGoods_Fail_EmptyGoodsId() throws Exception {
        //given
        Long goodsId = null;  // null 경로 변수

        Long memberId = 1L;
        GoodsRequestDto.OrderGoods orderGoods = new GoodsRequestDto.OrderGoods(1);
        // when
        // 상품이 없는 경우
        // then
        MvcResult mvcResult = mockMvc.perform(post("/goods/{goods_id}/order", goodsId)
                        .header("memberId", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderGoods)))
                .andReturn();
        // 응답 코드 404 확인
        assertEquals(404, mvcResult.getResponse().getStatus());
    }


    @Test
    @DisplayName("상품 주문수량 부족 실패")
    void orderGoods_Fail_InvalidQuantity() throws Exception {
        //given
        Long goodsId = 1L;
        Long memberId = 1L;
        GoodsRequestDto.OrderGoods orderGoods = new GoodsRequestDto.OrderGoods(0);
        // when
        MvcResult mvcResult = mockMvc.perform(post("/goods/{goods_id}/order", goodsId)
                        .header("memberId", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderGoods)))
                .andReturn();
        // then
        // 응답 코드 400 확인
        System.out.println("mvcResult.getResponse().getErrorMessage() = " + mvcResult.getResponse().getErrorMessage());
        assertEquals(400, mvcResult.getResponse().getStatus());
    }


    @Test
    @DisplayName("상품 주문수량 null 실패")
    void orderGoods_Fail_EmptyQuantity() throws Exception {
        //given
        Long goodsId = 1L;
        Long memberId = 1L;
        GoodsRequestDto.OrderGoods orderGoods = new GoodsRequestDto.OrderGoods(null);
        // when
        MvcResult mvcResult = mockMvc.perform(post("/goods/{goods_id}/order", goodsId)
                        .header("memberId", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderGoods)))
                .andReturn();
        // then
        // 응답 코드 400 확인
        System.out.println("mvcResult.getResponse().getErrorMessage() = " + mvcResult.getResponse().getErrorMessage());
        assertEquals(400, mvcResult.getResponse().getStatus());
    }


    /**
     * service에서 테스트 해야될 것 같음
     */
    @Test
    @DisplayName("상품 수량 부족 주문 실패")
    //db에 접근하여 파악필요
    void orderGoods_Fail_NotEnoughGoods() {
    }

    @Test
    @DisplayName("상품 상태 변경 실패")
        // db에 접근하여 파악필요
    void orderGoods_Fail_InvalidGoodsStatus() {
    }
}