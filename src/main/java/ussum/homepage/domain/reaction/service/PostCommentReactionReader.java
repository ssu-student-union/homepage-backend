package ussum.homepage.domain.reaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.reaction.PostCommentReaction;
import ussum.homepage.domain.reaction.PostCommentReactionRepository;
import ussum.homepage.domain.reaction.exception.PostCommentReactionException;

import java.util.Optional;

import static ussum.homepage.global.error.status.ErrorStatus.POST_COMMENT_REACTION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PostCommentReactionReader {
    private final PostCommentReactionRepository postCommentReactionRepository;

    public Optional<PostCommentReaction> getPostCommentReactionByUserIdAndCommentId(Long userId, Long commentId) {
        return postCommentReactionRepository.findByUserIdAndCommentId(userId, commentId);
    }

    public int getLikeCountByCommentId(Long commentId) {
        return postCommentReactionRepository.findAllPostCommentByCommentId(commentId).size();
    }

    public PostCommentReaction getPostCommentReactionWithCommentIdAndUserIdAndReaction(Long commentId, Long userId, String reaction) {
        return postCommentReactionRepository.findByCommentIdAndUserIdAndReaction(commentId, userId, reaction)
                .orElseThrow(() -> new PostCommentReactionException(POST_COMMENT_REACTION_NOT_FOUND));
    }
}
