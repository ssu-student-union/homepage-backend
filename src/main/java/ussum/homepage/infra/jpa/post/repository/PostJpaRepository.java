package ussum.homepage.infra.jpa.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ussum.homepage.infra.jpa.post.entity.BoardEntity;
import ussum.homepage.infra.jpa.post.entity.CategoryCode;
import ussum.homepage.infra.jpa.post.entity.OngoingStatus;
import ussum.homepage.infra.jpa.post.entity.PostEntity;

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
    @Query("SELECT p FROM PostEntity p WHERE p.boardEntity.id = :boardId")
    Page<PostEntity> findAllByBoardId(@Param("boardId") Long boardId, Pageable pageable);

    @Query("""
                    SELECT pe
                    FROM PostEntity pe
                    WHERE pe.boardEntity = :board 
                    AND (:q IS NULL OR pe.title LIKE %:q% OR pe.content LIKE %:q%)
                    AND (:categoryCode IS NULL OR pe.categoryEntity.categoryCode = :categoryCode)
                    ORDER BY pe.id DESC
            """)
    Page<PostEntity> findBySearchCriteria(Pageable pageable,
                                          @Param("board") BoardEntity board,
                                          @Param("q") String q,
                                          @Param("categoryCode") CategoryCode categoryCode);

}
