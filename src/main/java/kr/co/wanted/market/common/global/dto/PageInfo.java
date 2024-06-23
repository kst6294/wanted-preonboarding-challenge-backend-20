package kr.co.wanted.market.common.global.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static kr.co.wanted.market.common.global.constants.Constant.*;

public record PageInfo(Integer page, Integer size) {

    public PageInfo {

        if (page == null || page < PAGE_NUMBER_MIN) {
            page = PAGE_NUMBER_MIN;
        } else if (page > PAGE_NUMBER_MAX) {
            page = PAGE_NUMBER_MAX;
        }

        if (size == null || size < PAGE_SIZE_MIN) {
            size = PAGE_SIZE_MIN;
        } else if (size > PAGE_SIZE_MAX) {
            size = PAGE_SIZE_MAX;
        }
    }

    public PageRequest toPageRequest(Sort sort) {

        return PageRequest.of(
                this.page(),
                this.size(),
                sort
        );
    }

}
