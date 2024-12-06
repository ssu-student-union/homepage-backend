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

    public Boolean validatePostCommentReactionByCommentIdAndUserId(Long commentId, Long userId, String reaction) {
        return postCommentReactionRepository.findByCommentIdAndUserIdAndReaction(commentId, userId, reaction).isPresent();
    }


    public Integer getLikeCountOfPostComment(Long commentId) {
        return postCommentReactionRepository.findAllPostCommentByCommentId(commentId).size();
    }
}
