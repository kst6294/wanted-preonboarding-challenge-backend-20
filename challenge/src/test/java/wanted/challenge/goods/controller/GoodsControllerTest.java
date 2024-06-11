package wanted.challenge.goods.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import wanted.challenge.goods.dto.response.GoodsResponseDto;
import wanted.challenge.goods.entity.Goods;
import wanted.challenge.goods.entity.GoodsStatus;
import wanted.challenge.goods.mapper.GoodsMapper;
import wanted.challenge.goods.service.GoodsService;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GoodsController.class)
@AutoConfigureMockMvc
public class GoodsControllerTest {

    protected final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected GoodsService goodsService;

    @MockBean
    protected GoodsMapper mapper;

    // entity
    protected List<Goods> mockGoodsList = new ArrayList<>();
    protected Goods goods = new Goods("goodsName", 1000, GoodsStatus.SALE, 10);

    //dto
    protected List<GoodsResponseDto.GoodsListItem> mockResponseList = new ArrayList<>();
    protected GoodsResponseDto.GoodsListItem goodsListItem = new GoodsResponseDto.GoodsListItem(1L, "goodsName", 1000, GoodsResponseDto.GoodsStatus.판매중, 10);

    @BeforeEach
    public void setup() {
        objectMapper.registerModule(new JavaTimeModule());
        mockGoodsList.add(goods);
        mockResponseList.add(goodsListItem);
    }

}
