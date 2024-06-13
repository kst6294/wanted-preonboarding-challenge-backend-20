package org.example.wantedmarket.dto.common;

import lombok.Getter;

@Getter
public class DataResponseDto<T> extends ResponseDto {

    private final T data;

    public DataResponseDto(T data) {
        super(true);
        this.data = data;
    }

}
