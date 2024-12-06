package ussum.homepage.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    Optional<Board> findById(Long id);
    Optional<Board> findByBoardCode(String boardCode);
    Page<Board> findAll(Pageable pageable);
    Board save(Board board);
    void delete(Board board);
}
