package com.wanted.demo.domain.statements.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyProductStatementsResponseDTO {

    private Long id;
    private Long price;
    private String name;
    private LocalDateTime time;

    @Builder
    public MyProductStatementsResponseDTO(Long id, Long price, String name, LocalDateTime time){
        this.id = id;
        this.price = price;
        this.name = name;
        this.time = time;
    }
}
