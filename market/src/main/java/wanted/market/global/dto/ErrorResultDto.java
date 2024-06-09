package wanted.market.global.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResultDto {
    private HttpStatus status;
    private int code;
    private String message;

    @Builder
    public ErrorResultDto(HttpStatus status, int code, String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }
}