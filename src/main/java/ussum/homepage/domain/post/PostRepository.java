package ussum.homepage.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ussum.homepage.application.post.service.dto.response.SimplePostResponse;

import java.util.Optional;

public interface PostRepository {
    Optional<Post> findById(Long postId);
    Page<Post> findAllWithBoard(Pageable pageable, String boardCode);
    Optional<Post> findByBoardIdAndId(Long boardId,Long postId);
    Optional<Post> findByBoardIdAndIdForEditAndDelete(Long boardId,Long postId);
    Page<Post> findAllByBoardId(Long boardId, Pageable pageable);
    Post save(Post post);
    void delete(Post post);
    Page<Post> findBySearchCriteria(Pageable pageable,String boardCode, String q, String categoryCode);
    Page<SimplePostResponse> findPostDtoListByBoardCode(String boardCode, Pageable pageable);
    Post updatePostOngoingStatus(Long postId, String onGoingStatus, Category category);
    Page<Post> findAllByGroupCodeAndMemberCodeAndSubCategory(String groupCode, String memberCode, String subCategory, Pageable pageable);
}
