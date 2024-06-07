package wanted.Market.global.exception.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
@Getter
public enum TransactionErrorCode implements ErrorCode{
    INVALID_TRANSACTION(HttpStatus.UNAUTHORIZED, "현재 예약 중인 내역이 없습니다.");

    private HttpStatus httpStatus;
    private String message;
}
