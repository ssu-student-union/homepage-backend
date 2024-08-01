package ussum.homepage.application.post.service.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import ussum.homepage.global.common.PageInfo;

import java.util.List;
@Builder(access = AccessLevel.PRIVATE)
public record TopLikedPostListResponse(
        List<SimplePostResponse> posts,
        PageInfo pageInfo
) {
    public static TopLikedPostListResponse of(List<SimplePostResponse> posts, PageInfo pageInfo){
        return TopLikedPostListResponse.builder()
                .posts(posts)
                .pageInfo(pageInfo)
                .build();
    }
}
