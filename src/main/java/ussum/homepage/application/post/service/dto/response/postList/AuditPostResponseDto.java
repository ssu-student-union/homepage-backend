package ussum.homepage.application.post.service.dto.response.postList;

import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.post.Post;

@Getter
public class AuditPostResponseDto extends PostListResDto {
    private final String thumbNail;
    private final String status;

    @Builder
    private AuditPostResponseDto(Long postId, String title, String date, String thumbNail, String status) {
        super(postId, title, date);
        this.thumbNail = thumbNail;
        this.status = status;
    }

    public static AuditPostResponseDto of(Post post) {
        return AuditPostResponseDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
//                .content(post.getContent())
                .date(post.getCreatedAt().toString())
                .thumbNail(post.getThumbnailImage())
                .status(post.getStatus())
                .build();
    }
}