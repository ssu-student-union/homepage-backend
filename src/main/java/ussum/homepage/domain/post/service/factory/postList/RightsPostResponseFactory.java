package ussum.homepage.domain.post.service.factory.postList;

import ussum.homepage.application.post.service.dto.response.postList.PostListResDto;
import ussum.homepage.application.post.service.dto.response.postList.RightsPostResponse;
import ussum.homepage.domain.member.service.MemberReader;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.service.PostReader;
import ussum.homepage.domain.postlike.service.PostReactionReader;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.service.UserReader;

public class RightsPostResponseFactory implements PostListResponseFactory{
    @Override
    public PostListResDto createResponse(Post post, PostReader postReader, PostReactionReader postReactionReader,
                                         UserReader userReader, MemberReader memberReader) {

        User user = userReader.getUserWithId(post.getUserId());

        return RightsPostResponse.of(post,user);
    }
}
