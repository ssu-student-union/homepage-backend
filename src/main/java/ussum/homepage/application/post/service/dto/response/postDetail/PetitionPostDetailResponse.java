package ussum.homepage.application.post.service.dto.response.postDetail;

import lombok.Builder;
import lombok.Getter;
import ussum.homepage.application.comment.service.dto.response.PostOfficialCommentResponse;
import ussum.homepage.application.post.service.dto.response.FileResponse;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.user.User;

import java.util.List;

@Getter
public class PetitionPostDetailResponse extends PostDetailResDto {
    private final String studentId;
    private final Integer likeCount;
    private final List<PostOfficialCommentResponse> officialCommentList;
    private final List<FileResponse> fileResponseList;
    private final Boolean isLiked;

    @Builder
    private PetitionPostDetailResponse(Long postId, String categoryName, String authorName, String studentId, String title, String content, String createdAt, String lastEditedAt, Boolean isAuthor,
                                       Integer likeCount, List<FileResponse> fileResponseList, List<PostOfficialCommentResponse> officialCommentList,
                                       Boolean isLiked) {
        super(postId, categoryName, authorName, title, content, createdAt, lastEditedAt, isAuthor);
        this.studentId = studentId;
        this.likeCount = likeCount;
        this.fileResponseList = fileResponseList;
        this.officialCommentList = officialCommentList;
        this.isLiked = isLiked;
    }

    public static PetitionPostDetailResponse of(Post post, Boolean isAuthor, Boolean isLiked, User user, Integer likeCount, String category,
                                                List<FileResponse> fileResponseList, List<PostOfficialCommentResponse> officialCommentList) {
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
                .fileResponseList(fileResponseList)
                .officialCommentList(officialCommentList)
                .build();
    }

}
