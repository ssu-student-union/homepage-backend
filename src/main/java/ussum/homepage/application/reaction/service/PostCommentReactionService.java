package ussum.homepage.application.reaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.application.reaction.service.dto.request.PostCommentReactionCreateRequest;
import ussum.homepage.application.reaction.service.dto.response.PostCommentReactionResponse;
import ussum.homepage.domain.comment.PostComment;
import ussum.homepage.domain.comment.service.PostCommentReader;
import ussum.homepage.domain.reaction.PostCommentReaction;
import ussum.homepage.domain.reaction.service.PostCommentReactionAppender;
import ussum.homepage.domain.reaction.service.PostCommentReactionFormatter;

@Service
@RequiredArgsConstructor
//@Transactional(readOnly = true)
public class PostCommentReactionService {
    private final PostCommentReader postCommentReader;
    private final PostCommentReactionAppender postCommentReactionAppender;
    private final PostCommentReactionFormatter postCommentReactionFormatter;

    public PostCommentReactionResponse createCommentReaction(Long commentId, PostCommentReactionCreateRequest postCommentReactionCreateRequest) {
        PostComment postComment = postCommentReader.getPostComment(commentId);
        PostCommentReaction postCommentReaction = postCommentReactionAppender.createPostCommentReaction(postCommentReactionCreateRequest.toDomain(commentId));
        return postCommentReactionFormatter.format(
                postCommentReaction.getId(),
                commentId,
                postComment.getPostId(), postComment.getUserId(), /**postComment.getType(),**/null,
                postCommentReactionCreateRequest.reaction()
        );
    }

}
