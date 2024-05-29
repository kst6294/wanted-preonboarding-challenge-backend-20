package wanted.challenge.goods.service;

import org.springframework.stereotype.Service;
import wanted.challenge.goods.entity.Goods;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsService {
    public String createGoods() {
        return "상품등록완료";
    }

    public List<Goods> getGoodsList() {
        return new ArrayList<>();
    }

    public String getGoodsDetail() {
        return "goods detail";
    }

    public String deleteGoods() {
        return "삭제완료";
    }
}
