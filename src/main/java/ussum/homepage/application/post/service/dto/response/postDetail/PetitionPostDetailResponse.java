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
    //    private final String onGoingStatus;

    @Builder
    private PetitionPostDetailResponse(Long postId, String categoryName, String authorName, String studentId, String title, String content, String createdAt, String lastEditedAt, Boolean isAuthor,
                                       Integer likeCount, /*String onGoingStatus,*/ List<String> imageList, List<PostOfficialCommentResponse> officialCommentList) {
        super(postId, categoryName, authorName, title, content, createdAt, lastEditedAt, isAuthor);
        this.studentId = studentId;
        this.likeCount = likeCount;
        this.imageList = imageList;
        this.officialCommentList = officialCommentList;
//        this.onGoingStatus = onGoingStatus;
    }

    public static PetitionPostDetailResponse of(Post post, Boolean isAuthor, User user, Integer likeCount, String category, List<String> imageList,
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
                .likeCount(likeCount)
//                .onGoingStatus(post.getCategory())
                .imageList(imageList)
                .officialCommentList(officialCommentList)
                .build();
    }

}
