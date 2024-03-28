package ussum.homepage.infra.jpa.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ussum.homepage.infra.jpa.post.entity.BoardCode;
import ussum.homepage.infra.jpa.post.entity.BoardEntity;

import java.util.Optional;

public interface BoardJpaRepository extends JpaRepository<BoardEntity, Long> {
    Optional<BoardEntity> findByBoardCode(BoardCode boardCode);
}