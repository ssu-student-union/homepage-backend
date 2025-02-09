package ussum.homepage.domain.post.service.factory;

public class BoardFactory {
    public static BoardImpl createBoard(String boardCode, Long boardId) {
        if ("공지사항게시판".equals(boardCode)) {
            return new NoticeBoardImpl(boardId);
        } else if ("건의게시판".equals(boardCode)){
            return new SuggestionBoardImpl(boardId);
        } else if ("질의응답게시판".equals(boardCode)) {
            return  new QnABoardImpl(boardId);
        } else {
            return new GeneralBoardImpl(boardId);
        }
    }
}
