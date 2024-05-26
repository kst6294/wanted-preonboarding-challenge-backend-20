package wanted.preonboarding.backend.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import wanted.preonboarding.backend.domain.entity.Item;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemListResponse {

    private List<ItemResponse> items;

    public static ItemListResponse from(final List<Item> items) {
        List<ItemResponse> itemResponseList = new ArrayList<>();
        items.forEach((item) -> itemResponseList.add(ItemResponse.from(item)));
        return new ItemListResponse(itemResponseList);
    }
}
