package ussum.homepage.global.common;

import lombok.AccessLevel;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Builder(access = AccessLevel.PRIVATE)
public record PageInfo(
        int pageNum,
        int pageSize,
        long totalElements,
        long totalPages
) {
    public static PageInfo of(Page<?> page) {
        return PageInfo.builder()
                .pageNum(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
    public static Pageable of(int page, int take){
        return PageRequest.of(page, take);
    }
}
