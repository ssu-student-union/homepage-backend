package ussum.homepage.application.post.service.dto.response.postDetail;

import lombok.Builder;
import lombok.Getter;
import ussum.homepage.application.comment.service.dto.response.PostOfficialCommentResponse;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.user.User;

import java.util.List;

@Getter
public class PetitionPostDetailResponse extends PostDetailResDto {
    private final String studentId;
    private final Integer likeCount;
    private final List<PostOfficialCommentResponse> officialCommentList;
    private final List<String> imageList;
    private final Boolean isLiked;

    @Builder
    private PetitionPostDetailResponse(Long postId, String categoryName, String authorName, String studentId, String title, String content, String createdAt, String lastEditedAt, Boolean isAuthor,
                                       Integer likeCount, List<String> imageList, List<PostOfficialCommentResponse> officialCommentList,
                                       Boolean isLiked, List<String> canAuthority) {
        super(postId, categoryName, authorName, title, content, createdAt, lastEditedAt, isAuthor, canAuthority);
        this.studentId = studentId;
        this.likeCount = likeCount;
        this.imageList = imageList;
        this.officialCommentList = officialCommentList;
        this.isLiked = isLiked;
    }

    public static PetitionPostDetailResponse of(Post post, Boolean isAuthor, Boolean isLiked, User user, Integer likeCount, String category, List<String> imageList,
                                                List<PostOfficialCommentResponse> officialCommentList) {
        return PetitionPostDetailResponse.builder()
                .postId(post.getId())
                .categoryName(category)
                .authorName(user.getName())
                .studentId(user.getStudentId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .lastEditedAt(post.getLastEditedAt())
                .isAuthor(isAuthor)
                .isLiked(isLiked)
                .likeCount(likeCount)
                .imageList(imageList)
                .officialCommentList(officialCommentList)
                .build();
    }

}
