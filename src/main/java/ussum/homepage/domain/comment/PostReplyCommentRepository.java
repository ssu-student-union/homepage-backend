package ussum.homepage.domain.comment;


import java.util.List;
import java.util.Optional;

public interface PostReplyCommentRepository {
    List<PostReplyComment> findAllByCommentId(Long commentId);
    Optional<PostReplyComment> findById(Long replyCommentId);
    PostReplyComment save(PostReplyComment postReplyComment);
    PostReplyComment update(PostReplyComment domain);
    void delete(PostReplyComment postReplyComment);
    void deleteAllByUserId(Long userId);
}
