package wanted.preonboarding.backend.dto.request;

import lombok.Getter;

@Getter
public class ItemSaveRequest {

    private String name;
    private int price;
    private int stock;
}
