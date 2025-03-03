package ussum.homepage.infra.jpa.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ussum.homepage.infra.jpa.post.entity.BoardEntity;
import ussum.homepage.infra.jpa.post.entity.Category;
import ussum.homepage.infra.jpa.post.entity.PostEntity;

import java.util.List;
import java.util.Optional;

public interface PostJpaRepository extends JpaRepository<PostEntity,Long> {
    @Query("""
                SELECT pe
                FROM PostEntity pe
                WHERE pe.boardEntity = :board
                ORDER BY pe.id DESC
        """)
    Page<PostEntity> findAllByBoard(Pageable pageable, @Param("board") BoardEntity board);
    Optional<PostEntity> findByBoardEntityAndId(BoardEntity boardEntity, Long id);
    @Query("SELECT p FROM PostEntity p WHERE p.boardEntity.id = :boardId ORDER BY p.createdAt DESC")
    Page<PostEntity> findAllByBoardId(@Param("boardId") Long boardId, Pageable pageable);

    // 특정 사용자가 작성한 모든 게시글 조회 (페이징 없음)
    @Query("SELECT p FROM PostEntity p WHERE p.userEntity.id = :userId ORDER BY p.createdAt DESC")
    List<PostEntity> findByUserId(@Param("userId") Long userId);

//    @Query("""
//                    SELECT pe
//                    FROM PostEntity pe
//                    WHERE pe.boardEntity = :board
//                    AND (:q IS NULL OR pe.title LIKE %:q% OR pe.content LIKE %:q%)
//                    AND (:category IS NULL OR pe.categoryEntity.category = :category)
//                    ORDER BY pe.id DESC
//            """)
//    Page<PostEntity> findBySearchCriteria(Pageable pageable,
//                                          @Param("board") BoardEntity board,
//                                          @Param("q") String q,
//                                          @Param("category") CalendarCategory category);

}
