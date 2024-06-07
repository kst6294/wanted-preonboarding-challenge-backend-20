package wanted.Market.global.exception.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
@Getter
public enum ProductErrorCode implements ErrorCode{
    INVALID_PRODUCT_ID(HttpStatus.UNAUTHORIZED, "해당 제품 아이디를 찾을 수 없습니다.");
    private HttpStatus httpStatus;
    private String message;
}
