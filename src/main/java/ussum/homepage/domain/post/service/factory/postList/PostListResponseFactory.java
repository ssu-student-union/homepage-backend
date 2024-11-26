package ussum.homepage.domain.post.service.factory.postList;

import ussum.homepage.application.post.service.dto.response.postList.PostListResDto;
import ussum.homepage.application.post.service.dto.response.postList.RightsPostResponse;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.PostFile;
import ussum.homepage.domain.post.service.PostReader;
import ussum.homepage.domain.postlike.service.PostReactionReader;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.service.UserReader;

import java.util.List;

public interface PostListResponseFactory {
    PostListResDto createResponse(Post post, PostReader postReader, PostReactionReader postReactionReader, UserReader userReader);

    // 자료집 게시판을 위한 새로운 메서드
    default PostListResDto createDataResponse(Post post, List<PostFile> postFiles) {
        throw new UnsupportedOperationException("This operation is not supported for this board type.");
    }
    default PostListResDto createResponse(Post post, PostReader postReader, PostReactionReader postReactionReader,
                                          UserReader userReader,Long userId) {
        User user = userReader.getUserWithId(post.getUserId());

        return RightsPostResponse.of(post,user,userId);
    }
}
