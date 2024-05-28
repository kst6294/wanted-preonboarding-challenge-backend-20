package wanted.market.api.global.response.dto.internal;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor
public class PageInfoDto {

    private Integer totalPages;
    private Long totalElements;
    private Integer currentPage;
    private Integer pageSize;

    @Builder
    private PageInfoDto(Integer totalPages, Long totalElements, Integer currentPage, Integer pageSize) {
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public static PageInfoDto fromPage(Page<?> page){
        return PageInfoDto.builder()
                .totalPages(page.getTotalPages())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .currentPage(page.getNumber())
                .build();
    }
}
