package ussum.homepage.application.reaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.application.reaction.service.dto.request.PostReactionCreateRequest;
import ussum.homepage.application.reaction.service.dto.response.PostReactionResponse;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.service.PostReader;
import ussum.homepage.domain.postlike.PostReaction;
import ussum.homepage.domain.postlike.service.PostReactionAppender;
import ussum.homepage.domain.postlike.service.PostReactionFormatter;

@Service
@RequiredArgsConstructor
public class PostReactionService {
    private final PostReactionAppender postReactionAppender;
    private final PostReactionFormatter postReactionFormatter;

    //일단 반환 값 void말고 responseDto로 해놓고 나중에 없애도 될 것 같음.
    public PostReactionResponse createPostReaction(Long postId, PostReactionCreateRequest postReactionCreateRequest) {
        Long userId = 1L; //여기에 userId 추출하는 거 추가
        PostReaction postReaction = postReactionAppender.createPostReaction(postReactionCreateRequest.toDomain(postId, userId));
        return postReactionFormatter.format(
                postReaction.getId(),
                postId,
                userId,
                postReactionCreateRequest.reaction()
        );
    }
}
