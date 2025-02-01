package ussum.homepage.domain.post.service.factory.postList;

import ussum.homepage.application.post.service.dto.response.postList.PetitionPostResponse;
import ussum.homepage.application.post.service.dto.response.postList.PostListResDto;
import ussum.homepage.domain.member.service.MemberReader;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.service.PostReader;
import ussum.homepage.domain.postlike.service.PostReactionReader;
import ussum.homepage.domain.user.service.UserReader;

public class PetitionPostResponseFactory implements PostListResponseFactory {
    @Override
    public PostListResDto createResponse(Post post, PostReader postReader, PostReactionReader postReactionReader, UserReader userReader, MemberReader memberReader) {
        Integer likeCount = postReactionReader.countPostReactionsByType(post.getId(), "like");
        return PetitionPostResponse.of(post, likeCount);
    }
}
