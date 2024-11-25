package ussum.homepage.application.post.service.dto.response.postDetail;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import ussum.homepage.application.comment.service.dto.response.PostOfficialCommentResponse;
import ussum.homepage.application.post.service.dto.response.FileResponse;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.user.User;

@Getter
public class SuggestionPostDetailResponse extends PostDetailResDto {
    private List<FileResponse> fileResponseList;
    private List<PostOfficialCommentResponse> officialCommentList;
    private String studentId;

    @Builder
    public SuggestionPostDetailResponse(Long postId, String categoryName, String authorName, String studentId, String title, String content, String createdAt, String lastEditedAt, Boolean isAuthor,
                                        List<FileResponse> fileResponseList, List<PostOfficialCommentResponse> officialCommentList,
                                        List<String> canAuthority) {
        super(postId, categoryName, authorName, title, content, createdAt, lastEditedAt, isAuthor, canAuthority);
        this.fileResponseList = fileResponseList;
        this.officialCommentList = officialCommentList;
        this.studentId = studentId;
    }

    public static SuggestionPostDetailResponse of(Post post, Boolean isAuthor, User user, String categoryName, List<FileResponse> fileResponseList,
                                                  List<PostOfficialCommentResponse> officialCommentList) {
        return SuggestionPostDetailResponse.builder()
                .postId(post.getId())
                .authorName(user.getName())
                .categoryName(categoryName)
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .lastEditedAt(post.getLastEditedAt())
                .isAuthor(isAuthor)
                .fileResponseList(fileResponseList)
                .officialCommentList(officialCommentList)
                .build();
    }
}
