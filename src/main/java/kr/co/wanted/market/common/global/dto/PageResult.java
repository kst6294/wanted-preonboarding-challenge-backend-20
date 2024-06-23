package kr.co.wanted.market.common.global.dto;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public record PageResult<R>(List<R> list,
                            Integer page,
                            Integer size,
                            Integer totalPages,
                            Boolean first,
                            Boolean last) {

    /**
     * Entity 페이지 결과를 DTO Mapper 를 적용해 페이징 결과 생성
     *
     * @param page   Repository 에서 조회한 Entity 페이지 결과
     * @param mapper page 결과의 Entity 를 원하는 DTO 로 바꾸기 위한 Mapper
     * @param <E>    Entity Type
     * @param <R>    변환될 DTO Type
     * @return DTO 변환된 페이징 결과
     */
    public static <E, R> PageResult<R> of(Page<E> page, Function<E, R> mapper) {

        return new PageResult<>(
                page.map(mapper).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }


    /**
     * DTO 직접 조회 페이지 결과로 페이징 응답을 생성
     *
     * @param page Repository 에서 조회한 DTO 직접 조회 페이지 결과
     * @param <R>  DTO Type
     * @return 페이징 결과
     */
    public static <R> PageResult<R> of(Page<R> page) {

        return new PageResult<>(
                page.getContent(),
                page.getNumber(),
                page.getNumberOfElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }


}
