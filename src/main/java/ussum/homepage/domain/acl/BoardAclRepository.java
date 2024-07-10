package ussum.homepage.domain.acl;

import java.util.List;
import java.util.Optional;

public interface BoardAclRepository {
    List<BoardAcl> findByBoardId(Long boardId);
    BoardAcl save(BoardAcl boardAcl);
    BoardAcl update(BoardAcl boardAcl);
    Optional<BoardAcl> findById(Long boardAclId);
    void delete(BoardAcl boardAcl);

    List<BoardAcl> findByBoardIdDsl(Long boardId);

    Optional<BoardAcl> findByIdDsl(Long boardAclId);
}
