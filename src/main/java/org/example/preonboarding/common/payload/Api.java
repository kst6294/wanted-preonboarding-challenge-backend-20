package org.example.preonboarding.common.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.example.preonboarding.common.enums.ResultCode;

import java.time.LocalDateTime;

@Getter
@Builder
public class Api<T> {

    private final int code;
    @NonNull
    private final String message;
    private final LocalDateTime timestamp = LocalDateTime.now();
    @NonNull
    private final T data;

    public static class ApiBuilder<T> {
        public ApiBuilder<T> resultCode(ResultCode resultCode) {
            this.code = resultCode.code;
            this.message = resultCode.message;
            return this;
        }
    }
}
