package wanted.challenge.goods.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class GoodsRequestDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CreateGoods {
        private String name;
        private int price;
        private int quantity;
    }
}
