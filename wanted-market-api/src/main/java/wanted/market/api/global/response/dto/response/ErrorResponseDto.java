package wanted.market.api.global.response.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponseDto {

    private String domain;
    private String message;

    @Builder
    public ErrorResponseDto(String domain, String message){
        this.domain = domain;
        this.message = message;
    }

}
