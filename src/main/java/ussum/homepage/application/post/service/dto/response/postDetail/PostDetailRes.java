package ussum.homepage.application.post.service.dto.response.postDetail;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record PostDetailRes<T extends PostDetailResDto>(
        T postDetailResDto
) {
    public static <T extends PostDetailResDto> PostDetailRes of(T postDetailResDto) {
        return new PostDetailRes<>(postDetailResDto);
    }
}
