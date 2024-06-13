package org.example.wantedmarket.dto.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.example.wantedmarket.exception.ErrorCode;

@Getter
@ToString
@RequiredArgsConstructor
public class ResponseDto {

    private final Boolean success;

}
