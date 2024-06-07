package wanted.Market.global.exception.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum AuthErrorCode implements ErrorCode{
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "Access Token이 만료되었습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "Access Token을 찾을 수 없습니다."),
    INVALID_JSON_FORMAT(HttpStatus.BAD_REQUEST, "JSON 포멧 오류"),
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "인증 과정에서 오류가 발생했습니다.");
    private HttpStatus httpStatus;
    private String message;
}