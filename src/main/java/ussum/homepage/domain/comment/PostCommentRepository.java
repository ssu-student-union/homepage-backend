package ussum.homepage.domain.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostCommentRepository {
    Page<PostComment> findAllByPostId(Pageable pageable, Long postId);
    List<PostComment> findAllByPostId(Long postId);
    List<PostComment> findAllByPostIdOrderByLikesDesc(Long postId);
    List<PostComment> findAllByPostIdOrderByCreatedAtDesc(Long postId);
    List<PostComment> findAllByPostIdAndCommentType(Long postId, String commentType);
    Optional<PostComment> findByPostIdAndUserId(Long postId, Long userId);
    Optional<PostComment> findById(Long id);
    PostComment save(PostComment postComment);
    PostComment update(PostComment postComment);
    void delete(PostComment postComment);
    void deleteWithoutCommentType(PostComment postComment);
    Long getCommentCountByPostId(Long postId);
    void deleteAllByUserId(Long userId);
}
