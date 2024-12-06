//package ussum.homepage.domain.post.service.formatter;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import ussum.homepage.application.post.service.dto.BoardResponse;
//import ussum.homepage.domain.post.Board;
//import ussum.homepage.domain.post.service.BoardReader;
//
//@Service
//@RequiredArgsConstructor
//public class BoardFormatter implements ussum.homepage.domain.post.service.BoardFormatter {
//    private final BoardReader boardReader;
//    @Override
//    public BoardResponse format(Long boardId){
//        final Board board = boardReader.getBoardWithId(boardId);
//        return BoardResponse.of(board);
//    }
//}
