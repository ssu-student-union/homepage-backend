package ussum.homepage.application.post.service.dto.response.postList;

import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.user.User;

@Getter
public class SuggestionPostResponse extends PostListResDto {

    private final String author;
    private final Long userId;

    @Builder
    private SuggestionPostResponse(Long postId, String title, String content, String date, String category, String author, Long userId) {
        super(postId, title, content, date, category);
        this.author = author;
        this.userId = userId;
    }

    public static SuggestionPostResponse of(Post post, User user) {
        return SuggestionPostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .date(post.getCreatedAt())
                .content(post.getContent())
                .category(post.getCategory())
                .author(user.getName())
                .userId(user.getId())
                .build();
    }

}
