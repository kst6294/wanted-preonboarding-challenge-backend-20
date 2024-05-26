package wanted.preonboarding.backend.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import wanted.preonboarding.backend.domain.entity.Item;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemResponse {

    private int price;
    private Item.ItemStatus status;

    public static ItemResponse from(final Item item) {
        return new ItemResponse(item.getPrice(), item.getStatus());
    }
}
