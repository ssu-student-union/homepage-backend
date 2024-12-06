package ussum.homepage.application.post.service.dto;

import ussum.homepage.domain.post.Board;

public record
BoardResponse(
        Long id,
        String boardCode,
        String name,
        String createdAt
) {
    public static BoardResponse of(Board board){
        return new BoardResponse(
                board.getId(),
                board.getBoardCode(),
                board.getName(),
                board.getCreatedAt()
        );
    }
}
