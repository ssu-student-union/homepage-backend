package ussum.homepage.domain.post.service.factory.postList;

import ussum.homepage.application.post.service.dto.response.postList.PostListResDto;
import ussum.homepage.application.post.service.dto.response.postList.SuggestionPostResponse;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.service.PostReader;
import ussum.homepage.domain.postlike.service.PostReactionReader;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.service.UserReader;

public class SuggestionPostResponseFactory implements PostListResponseFactory {

    @Override
    public PostListResDto createResponse(Post post, PostReader postReader, PostReactionReader postReactionReader, UserReader userReader) {
        User user = userReader.getUserWithId(post.getUserId());
        return SuggestionPostResponse.of(post, user);
    }

}