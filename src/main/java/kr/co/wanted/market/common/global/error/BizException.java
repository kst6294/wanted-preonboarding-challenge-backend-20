package kr.co.wanted.market.common.global.error;

import kr.co.wanted.market.common.global.enums.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BizException extends RuntimeException {

    private final ErrorCode errorCode;

    public BizException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BizException(ErrorCode errorCode, String customMessage) {
        super(customMessage);

        this.errorCode = errorCode;
    }

    public HttpStatus getHttpStatus() {

        return errorCode.getHttpStatus();
    }


    public String getCode() {

        return errorCode.name();
    }
}
