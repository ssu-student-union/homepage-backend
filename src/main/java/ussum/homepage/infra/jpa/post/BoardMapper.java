package ussum.homepage.infra.jpa.post;

import org.springframework.stereotype.Component;
import ussum.homepage.domain.post.Board;
import ussum.homepage.infra.jpa.post.entity.BoardCode;
import ussum.homepage.infra.jpa.post.entity.BoardEntity;

@Component
public class BoardMapper {
    public Board toDomain(BoardEntity boardEntity){
        return Board.of(
                boardEntity.getId(),
                String.valueOf(boardEntity.getBoardCode()),
                boardEntity.getName(),
                boardEntity.getCreatedAt(),
                boardEntity.getUpdatedAt()
                );
    }
    public BoardEntity toEntity(Board board){
        return BoardEntity.of(
                board.getId(),
                BoardCode.getEnumBoardCodeFromStringBoardCode(board.getBoardCode()),
                board.getName()
        );
    }
}
