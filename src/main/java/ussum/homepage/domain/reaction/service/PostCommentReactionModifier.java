package ussum.homepage.domain.reaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.reaction.PostCommentReactionRepository;

@Service
@RequiredArgsConstructor
public class PostCommentReactionModifier {
    private final PostCommentReactionRepository postCommentReactionRepository;
    private final PostCommentReactionReader postCommentReactionReader;

    public void deletePostCommentReaction(Long commentId, Long userId, String reaction) {
        postCommentReactionRepository.delete(
                postCommentReactionReader.getPostCommentReactionWithCommentIdAndUserId(commentId, userId, reaction)
        );
    }
}
