package ussum.homepage.application.post.service.dto.request;

import ussum.homepage.domain.post.Board;

public record BoardUpdateRequest(
        String name,
        String boardCode
) {
    public Board toDomain(Long boarId){
        return Board.of(
                boarId,
                boardCode,
                name,
                null,
                null
        );
    }
}
