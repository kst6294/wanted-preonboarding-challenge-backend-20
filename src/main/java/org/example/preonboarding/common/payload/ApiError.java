package org.example.preonboarding.common.payload;


import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.example.preonboarding.common.enums.ResultCode;

import java.time.LocalDateTime;

@Getter
@Builder
public class ApiError<T> {
    private final int code;
    @NonNull
    private final String message;
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final String data;

    public static class ApiErrorBuilder<T> {
        public ApiError.ApiErrorBuilder<T> resultCode(ResultCode resultCode) {
            this.code = resultCode.code;
            this.message = resultCode.message;
            return this;
        }
    }
}
