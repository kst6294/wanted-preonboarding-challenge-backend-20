package wanted.Market.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wanted.Market.global.exception.ErrorCode.ErrorCode;

@AllArgsConstructor
@Getter
public class AppException extends RuntimeException {
    private ErrorCode errorCode;
}
