package ussum.homepage.domain.reaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.reaction.PostCommentReactionRepository;
import ussum.homepage.domain.reaction.exception.PostCommentReactionException;

import static ussum.homepage.global.error.status.ErrorStatus.POST_COMMENT_REACTION_IS_ALREADY_EXIST;

@Service
@RequiredArgsConstructor
public class PostCommentReactionManager {
    private final PostCommentReactionRepository postCommentReactionRepository;

    public void validatePostCommentReactionByCommentIdAndUserId(Long commentId, Long userId, String reaction) {
        postCommentReactionRepository.findByCommentIdAndUserId(commentId, userId, reaction)
                .ifPresent(postCommentReaction -> {
                    throw new PostCommentReactionException(POST_COMMENT_REACTION_IS_ALREADY_EXIST);
                });
    }

    public Integer getLikeCountOfPostComment(Long commentId) {
        return postCommentReactionRepository.findAllPostCommentByCommentId(commentId).size();
    }
}
