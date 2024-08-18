package ussum.homepage.domain.post.service.factory;

public class BoardFactory {
    public static BoardImpl createBoard(String boardCode) {
        if ("공지사항게시판".equals(boardCode)) {
            return new NoticeBoardImpl();
        } else {
            return new GeneralBoardImpl();
        }
    }
}
