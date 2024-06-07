package wanted.Market.global.exception.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum UserErrorCode implements ErrorCode {
    USERNAME_DUPLICATED(HttpStatus.CONFLICT, "유저 중복"),
    FAILD_LOGIN(HttpStatus.NOT_FOUND, "유저가 없거나 비밀번호가 틀립니다.");
    private HttpStatus httpStatus;
    private String message;
}
