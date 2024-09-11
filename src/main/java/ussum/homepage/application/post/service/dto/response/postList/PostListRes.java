package ussum.homepage.application.post.service.dto.response.postList;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import ussum.homepage.global.common.PageInfo;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record PostListRes<T extends PostListResDto>(
        List<T> postListResDto,
        List<String> allowedAuthorities,
        List<String> deniedAuthorities,
        PageInfo pageInfo
) {
    public static <T extends PostListResDto> PostListRes<T> of(List<T> postListResDto, PageInfo pageInfo) {
        return new PostListRes<>(postListResDto, List.of(), List.of(), pageInfo);
    }

    public PostListRes<T> validAuthorities(List<String> allowedAuthorities, List<String> deniedAuthorities) {
        return new PostListRes<>(this.postListResDto, allowedAuthorities, deniedAuthorities, this.pageInfo);
    }
}