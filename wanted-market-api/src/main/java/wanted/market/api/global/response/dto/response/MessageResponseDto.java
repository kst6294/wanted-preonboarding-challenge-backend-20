package wanted.market.api.global.response.dto.response;

import lombok.Builder;
import lombok.Getter;
import wanted.market.api.global.response.enums.ResponseMessage;
@Getter
public class MessageResponseDto {


    private String message;

    @Builder
    public MessageResponseDto(String message){
        this.message = message;
    }

}
