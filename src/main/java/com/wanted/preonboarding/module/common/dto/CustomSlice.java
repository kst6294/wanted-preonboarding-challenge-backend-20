package com.wanted.preonboarding.module.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class CustomSlice<T> {
    private final List<T> content;
    private final boolean last;
    private final boolean first;
    private final int number;
    private final Sort sort;
    private final int size;
    private final int numberOfElements;
    private final boolean empty;
    @Setter
    private Long lastDomainId;
    @Setter
    private String cursorValue;

}
