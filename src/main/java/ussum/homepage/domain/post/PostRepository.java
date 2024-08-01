package ussum.homepage.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ussum.homepage.application.post.service.dto.response.SimplePostResponse;
import ussum.homepage.infra.jpa.post.dto.SimplePostDto;


import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Optional<Post> findById(Long postId);
    Page<Post> findAllWithBoard(Pageable pageable, String boardCode);
    Optional<Post> findByBoardIdAndId(Long boardId,Long postId);
    Optional<Post> findByBoardIdAndIdForEditAndDelete(Long boardId,Long postId);
    List<Post> findByBoard(Long postId);
    Post save(Post post);
    void delete(Post post);
    Page<Post> findBySearchCriteria(Pageable pageable,String boardCode, String q, String categoryCode);
    Page<SimplePostResponse> findPostDtoListByBoardCode(String boardCode, Pageable pageable);
}
