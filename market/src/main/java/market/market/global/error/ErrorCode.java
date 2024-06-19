package market.market.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    BAD_REQUEST(400, "Bad Request"),

    USER_NOT_FOUND(404, "User Not Found"),
    USER_ALREADY_EXISTS(409, "User Already Exists"),
    TRANSACTION_STATUS_ALREADY_EXISTS(409, "Transaction Status Already Exists"),
    TRANSACTION_STATUS_NOT_READY(409, "Transaction Status Not Ready"),
    USER_MISS_MATCHED(409, "User Miss Match"),
    PRODUCT_ZERO_QUANTITY(409, "Product Zero Quantity"),
    PRODUCT_COMPLETION_QUANTITY(409, "Product Completion Quantity"),
    ROLE_NOT_FOUND(404,"Role Not Found"),
    PRODUCT_NOT_FOUND(404, "Product Not Found"),
    TRANSACTION_NOT_FOUND(404, "Transaction Not Found"),
    EMAIL_NOT_FOUND(404, "Email Not Found"),
    EMAIL_MISS_MATCHED(409, "Email Miss Match"),
    CODE_NOT_FOUND(404, "Code Not Found"),
    CODE_EXPIRED(401, "Code Expired"),
    ACCOUNT_ID_NOT_CHECK(401, "AccountId Not Check"),
    PASSWORD_MISS_MATCHED(409, "Password Miss Match"),
    ORIGIN_PASSWORD_MISS_MATCHED(409, "Origin Password Miss Match"),
    CHANGE_PASSWORD_MISS_MATCHED(409, "Change Password Miss Match"),

    EXIST_EMAIL(401, "Exist Email"),
    EXIST_USER(401, "Exist User"),

    NOTIFICATION_NOT_FOUND(404, "Notification Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    JWT_EXPIRED(401, "Jwt Expired"),
    JWT_INVALID(401, "Jwt Invalid");

    private final Integer httpStatus;

    private final String message;
}
