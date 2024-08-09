package ussum.homepage.domain.reaction;

import java.util.List;
import java.util.Optional;

public interface PostReplyCommentReactionRepository {
    List<PostReplyCommentReaction> findAllByReplyCommentId(Long replyCommentId);
    Optional<PostReplyCommentReaction> findByUserIdAndReplyCommentId(Long userId, Long replyCommentId);
    PostReplyCommentReaction save(PostReplyCommentReaction postReplyCommentReaction);
    void delete(PostReplyCommentReaction postReplyCommentReaction);
}