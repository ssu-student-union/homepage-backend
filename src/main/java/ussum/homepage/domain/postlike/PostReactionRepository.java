package ussum.homepage.domain.postlike;

import java.util.Optional;

public interface PostReactionRepository {
    PostReaction save(PostReaction postReaction);

    Optional<PostReaction> findByUserIdAndPostId(Long userId, Long postId);

    void delete(PostReaction postReaction);

    Optional<PostReaction> findByPostIdAndUserId(Long postId, Long userId, String reaction);
    Integer countByPostIdAndReactionType(Long postId, String reactionType);
}
