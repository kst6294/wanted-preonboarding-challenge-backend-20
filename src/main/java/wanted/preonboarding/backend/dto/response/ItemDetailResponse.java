package wanted.preonboarding.backend.dto.response;

import lombok.Getter;
import wanted.preonboarding.backend.domain.entity.Item;
import wanted.preonboarding.backend.domain.entity.Orders;

@Getter
public class ItemDetailResponse {

    private int price;
    private Item.ItemStatus status;
    private OrderResponse order;

    public ItemDetailResponse() {
    }

    private ItemDetailResponse(int price, Item.ItemStatus status) {
        this.price = price;
        this.status = status;
    }

    private ItemDetailResponse(int price, Item.ItemStatus status, OrderResponse order) {
        this.price = price;
        this.status = status;
        this.order = order;
    }

    public static ItemDetailResponse from(final Item item) {
        return new ItemDetailResponse(item.getPrice(), item.getStatus());
    }

    public static ItemDetailResponse of(final Item item, final Orders order) {
        return new ItemDetailResponse(item.getPrice(), item.getStatus(), OrderResponse.from(order));
    }
}
