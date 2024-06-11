package wanted.challenge.goods.controller.post.create;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import wanted.challenge.exception.GlobalExceptionHandler;
import wanted.challenge.goods.controller.GoodsControllerTest;
import wanted.challenge.goods.dto.request.GoodsRequestDto;
import wanted.challenge.goods.entity.Goods;
import wanted.challenge.goods.entity.GoodsStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Import(GlobalExceptionHandler.class)  // 전역 예외 처리기 포함
class GoodsCreatedControllerTest extends GoodsControllerTest {

    @Test
    @DisplayName("상품 생성 성공")
    void success_CreateGoods() throws Exception {
        // given
        Long sellerId = 1L;
        Goods goods2 = new Goods("goodsName", 1000, GoodsStatus.SALE , 10);
        GoodsRequestDto.CreateGoods createGoods = new GoodsRequestDto.CreateGoods("goodsName", 1000, 10);

        // when
        Mockito.when(mapper.toGoods(createGoods)).thenReturn(goods2);
        Mockito.when(goodsService.createGoods(sellerId, goods2)).thenReturn(goods2);
        MvcResult mvcResult = mockMvc.perform(post("/goods")
                        .header("memberId", sellerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createGoods)))
                .andExpect(status().isCreated())
                .andReturn();

        // then
        // 응답 코드 200 확인
        assertEquals(201, mvcResult.getResponse().getStatus());
    }

    @Test
    @DisplayName("상품 생성 실패 - 회원이 아님")
    void fail_NotMember() throws Exception {
        // given
        GoodsRequestDto.CreateGoods createGoods = new GoodsRequestDto.CreateGoods("goodsName", 1000, 10);
        // when
        // memberID가 없는 경우
        Mockito.when(mapper.toGoods(createGoods)).thenReturn(goods);
        MvcResult mvcResult = mockMvc.perform(post("/goods")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createGoods)))
                .andReturn();
        // then
        // 응답 코드 400 확인
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    @DisplayName("상품 생성 실패 - 상품명이 중복됨")  //db를 접근하기 때문에 service에서 테스트 해야될 것 같음
    void fail_DuplicateGoodsName() throws Exception {
        // given

        // when
        // then
    }

    @ParameterizedTest(name = "{index} => name={0}, price={1}, quantity={2}")
    @DisplayName("상품 생성 실패 - 상품명, 가격, 수량이 없음")
    @CsvSource({
            ", 1000, 10",
            "goodsName, , 10",
            "goodsName, 0, 10",
            "goodsName, 1000, ",
            "goodsName, 1000, 0",
    })
    void fail_InvalidGoodsInfo(String name, Integer price, Integer quantity) throws Exception {
        // given
        Long sellerId = 1L;
        GoodsRequestDto.CreateGoods createGoods = new GoodsRequestDto.CreateGoods(name, price, quantity);
        // when
        Mockito.when(mapper.toGoods(createGoods)).thenReturn(goods);
        MvcResult mvcResult = mockMvc.perform(post("/goods")
                        .header("memberId", sellerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createGoods)))
                .andReturn();
        // then
        // 응답 코드 400 확인
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

}

