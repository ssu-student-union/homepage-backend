package ussum.homepage.application.post.service.dto.response;

import ussum.homepage.domain.post.Category;

import java.time.LocalDateTime;

public record CategoryResponse(
        Long id,
        String majorCode,
        String name
) {
    public static CategoryResponse of(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getCategoryCode(),
                category.getName()
        );

    }
}

