package ussum.homepage.application.post.service.dto.response.postDetail;

import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record PostDetailRes<T extends PostDetailResDto>(
        T postDetailResDto
) {
    public static <T extends PostDetailResDto> PostDetailRes of(T postDetailResDto) {
        return new PostDetailRes<>(postDetailResDto);
    }

    public void validAuthority(List<String> canAuthorityList) {
        this.postDetailResDto.canAuthority(canAuthorityList);
    }
}
