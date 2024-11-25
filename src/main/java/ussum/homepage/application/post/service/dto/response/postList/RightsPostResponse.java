package ussum.homepage.application.post.service.dto.response.postList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.user.User;

@Getter
public class RightsPostResponse extends PostListResDto{
    private final String reportName;
    @JsonProperty("isAuthor")
    private final boolean isAuthor;
    @JsonIgnore
    public boolean getAuthor() {
        return isAuthor;
    }

    @Builder
    protected RightsPostResponse(Long postId, String title, String content, String date, String category, String reportName,
                                 boolean isAuthor) {
        super(postId, title, content, date, category);
        this.reportName = reportName;
        this.isAuthor = isAuthor;
    }

    public static RightsPostResponse of(Post post, User user,Long userId) {
        return RightsPostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .date(post.getCreatedAt())
                .category(post.getCategory())
                .reportName(user.getName())
                .isAuthor(userId.equals(user.getId()))
                .build();
    }

    public static RightsPostResponse of(Post post, User user) {
        return RightsPostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .date(post.getCreatedAt())
                .category(post.getCategory())
                .reportName(user.getName())
                .build();
    }
}
