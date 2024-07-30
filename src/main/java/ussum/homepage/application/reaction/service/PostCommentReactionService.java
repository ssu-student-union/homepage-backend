package ussum.homepage.application.reaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.application.reaction.service.dto.request.PostCommentReactionCreateRequest;
import ussum.homepage.application.reaction.service.dto.response.PostCommentReactionResponse;
import ussum.homepage.domain.comment.PostComment;
import ussum.homepage.domain.reaction.service.PostCommentReactionManager;
import ussum.homepage.domain.comment.service.PostCommentReader;
import ussum.homepage.domain.reaction.PostCommentReaction;
import ussum.homepage.domain.reaction.service.PostCommentReactionAppender;
import ussum.homepage.domain.reaction.service.PostCommentReactionFormatter;
import ussum.homepage.domain.reaction.service.PostCommentReactionModifier;
import ussum.homepage.domain.reaction.service.PostCommentReactionReader;

@Service
@RequiredArgsConstructor
//@Transactional(readOnly = true)
public class PostCommentReactionService {
    private final PostCommentReader postCommentReader;
    private final PostCommentReactionAppender postCommentReactionAppender;
    private final PostCommentReactionModifier postCommentReactionModifier;
    private final PostCommentReactionManager postCommentReactionManager;
    private final PostCommentReactionFormatter postCommentReactionFormatter;
    private final PostCommentReactionReader postCommentReactionReader;

    public PostCommentReactionResponse createCommentReaction(Long commentId, PostCommentReactionCreateRequest postCommentReactionCreateRequest) {
        PostComment postComment = postCommentReader.getPostComment(commentId);
        Long userId = 1L; //여기에 userId 추출하는 거 추가
        postCommentReactionManager.validatePostCommentReactionByCommentIdAndUserId(commentId, userId, postCommentReactionCreateRequest.reaction()); //해당 유저가 해당 댓글에 좋아요를 이미 눌렀는지 안눌렀는지 검증
        PostCommentReaction postCommentReaction = postCommentReactionAppender.createPostCommentReaction(postCommentReactionCreateRequest.toDomain(commentId, userId));
        return postCommentReactionFormatter.format(
                postCommentReaction.getId(),
                commentId,
                postComment.getPostId(), postComment.getUserId(), /**postComment.getType(),**/null,
                postCommentReactionCreateRequest.reaction()
        );
    }

    @Transactional
    public void deletePostCommentReaction(Long commentId, PostCommentReactionCreateRequest postCommentReactionCreateRequest) {
        Long userId = 1L; //여기에 userId 추출하는 거 추가
//        PostCommentReaction postCommentReaction = postCommentReactionReader.getPostCommentReactionWithCommentIdAndUserId(commentId, userId, postCommentReactionCreateRequest.reaction());
        postCommentReactionModifier.deletePostCommentReaction(commentId, userId, postCommentReactionCreateRequest.reaction());
//        postCommentReactionModifier.deletePostCommentReaction(postCommentReaction.getPostCommentId(), postCommentReaction.getUserId(),
//                postCommentReaction.getReactionType());
    }
}
