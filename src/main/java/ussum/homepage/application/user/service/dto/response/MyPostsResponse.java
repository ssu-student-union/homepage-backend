package ussum.homepage.application.user.service.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ussum.homepage.application.post.service.dto.response.postList.MyPostResponse;
import ussum.homepage.global.common.PageInfo;

import java.util.List;

@Builder
@RequiredArgsConstructor
@Getter
public class MyPostsResponse {

    private final List<MyPostResponse> myPosts;
    private final PageInfo pageInfo;

    public static MyPostsResponse of(List<MyPostResponse> myPosts, PageInfo pageInfo) {
        return MyPostsResponse.builder()
                .myPosts(myPosts)
                .pageInfo(pageInfo)
                .build();
    }

}


