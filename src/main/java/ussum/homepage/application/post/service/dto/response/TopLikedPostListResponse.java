package ussum.homepage.application.post.service.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import ussum.homepage.global.common.PageInfo;

import java.util.List;
@Builder(access = AccessLevel.PRIVATE)
public record TopLikedPostListResponse(
        List<SimplePostResponse> postListResDto,
        PageInfo pageInfo
) {
    public static TopLikedPostListResponse of(List<SimplePostResponse> postListResDto, PageInfo pageInfo){
        return TopLikedPostListResponse.builder()
                .postListResDto(postListResDto)
                .pageInfo(pageInfo)
                .build();
    }
}
