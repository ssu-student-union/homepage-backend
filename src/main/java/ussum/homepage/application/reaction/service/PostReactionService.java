package ussum.homepage.application.reaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.application.reaction.service.dto.request.PostReactionCreateRequest;
import ussum.homepage.application.reaction.service.dto.response.PostReactionResponse;
import ussum.homepage.domain.postlike.PostReaction;
import ussum.homepage.domain.postlike.service.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostReactionService {
    private final PostReactionAppender postReactionAppender;
    private final PostReactionModifier postReactionModifier;
    private final PostReactionManager postReactionManager;
    private final PostReactionFormatter postReactionFormatter;


    //일단 반환 값 void말고 responseDto로 해놓고 나중에 없애도 될 것 같음.
    @Transactional
    public PostReactionResponse createPostReaction(Long postId, PostReactionCreateRequest postReactionCreateRequest) {
        Long userId = 1L; //여기에 userId 추출하는 거 추가
        postReactionManager.validatePostReactionByCommentIdAndUserId(postId, userId, postReactionCreateRequest.reaction());
        PostReaction postReaction = postReactionAppender.createPostReaction(postReactionCreateRequest.toDomain(postId, userId));
        return postReactionFormatter.format(
                postReaction.getId(),
                postId,
                userId,
                postReactionCreateRequest.reaction()
        );
    }

    @Transactional
    public void deletePostReaction(Long postId, PostReactionCreateRequest postReactionCreateRequest) {
        Long userId = 1L; //여기에 userId 추출하는 거 추가
        postReactionModifier.deletePostReaction(postId, userId, postReactionCreateRequest.reaction());
    }
}
