package ussum.homepage.application.post.service.dto;

import org.springframework.data.domain.Page;
import ussum.homepage.domain.post.Board;

import java.util.List;
import java.util.function.Function;

public record BoardListResponse(
        List<BoardResponse> boards,
        Integer total
) {
    public static BoardListResponse of(Page<Board> boards, Long total, Function<Long, BoardResponse> formatter){
        return new BoardListResponse(
                boards.map(board -> formatter.apply(board.getId())).toList(),
                Math.toIntExact(total)
        );
    }
}
