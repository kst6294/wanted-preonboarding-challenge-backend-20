package wanted.market.global.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResultDto {
    private HttpStatus httpStatus;
    private int code;
    private String message;

    @Builder
    public ErrorResultDto(HttpStatus httpStatus, int code, String message){
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}