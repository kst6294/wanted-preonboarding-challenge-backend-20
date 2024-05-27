package wanted.market.api.global.response.dto;

import lombok.Builder;
import wanted.market.api.global.response.enums.ResponseMessage;

public class MessageResponseDto {

    private ResponseMessage message;

    @Builder
    public MessageResponseDto(ResponseMessage message){
        this.message = message;
    }

}
