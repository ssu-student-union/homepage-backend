package ussum.homepage.application.post.service.dto.response.postList;

import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.post.Post;
import ussum.homepage.infra.jpa.post.entity.BoardCode;

@Getter
public class IntegratedSearchPostResponse extends PostListResDto{
    private final String boardCode;

    @Builder
    private IntegratedSearchPostResponse(Long postId, String title, String content, String date, String category, String boardCode) {
        super(postId, title, content, date, category);
        this.boardCode = boardCode;
    }

    public static MyPostResponse of(Post post) {
        return MyPostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .date(post.getCreatedAt())
                .content(post.getContent())
                .category(post.getCategory())
                .boardCode(BoardCode.getEnumBoardCodeFromBoardId(post.getBoardId()).getStringBoardCode())
                .build();
    }
}
