package ussum.homepage.domain.postlike;


import java.util.Optional;

public interface PostReactionRepository {
    PostReaction save(PostReaction postReaction);
    void delete(PostReaction postReaction);
    Optional<PostReaction> findByPostIdAndUserId(Long postId, Long userId, String reaction);
}
