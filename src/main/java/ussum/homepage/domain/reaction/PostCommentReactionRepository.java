package ussum.homepage.domain.reaction;

import java.util.List;
import java.util.Optional;

public interface PostCommentReactionRepository {
    PostCommentReaction save(PostCommentReaction postCommentReaction);
    void delete(PostCommentReaction postCommentReaction);
    List<PostCommentReaction> findAllPostCommentByCommentId(Long commentId);
    Optional<PostCommentReaction> findByCommentIdAndUserIdAndReaction(Long commentId, Long userId, String reaction);
    Optional<PostCommentReaction> findByUserIdAndCommentId(Long userId, Long commentId);

}

