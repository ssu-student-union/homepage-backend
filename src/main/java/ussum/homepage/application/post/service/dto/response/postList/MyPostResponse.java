package ussum.homepage.application.post.service.dto.response.postList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.user.User;
import ussum.homepage.infra.jpa.post.entity.BoardCode;

@Getter
public class MyPostResponse extends PostListResDto {

    @JsonIgnore  // category 응답에서 제외됨
    private final String category;
    private final int commentCount;
    private final String boardCode;

    @Builder
    private MyPostResponse(Long postId, String title, String content, String date, String category, int commentCount, String boardCode) {
        super(postId, title, content, date, category);
        this.commentCount = commentCount;
        this.boardCode = boardCode;
        this.category = category;
    }

    public static MyPostResponse of(Post post, int commentCount) {
        return MyPostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .date(post.getCreatedAt())
                .content(post.getContent())
                .category(post.getCategory())
                .commentCount(commentCount)
                .boardCode(BoardCode.getEnumBoardCodeFromBoardId(post.getBoardId()).getStringBoardCode())
                .build();
    }
}
