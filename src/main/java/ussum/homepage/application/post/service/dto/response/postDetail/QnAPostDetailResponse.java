package ussum.homepage.application.post.service.dto.response.postDetail;

import lombok.Builder;
import lombok.Getter;
import ussum.homepage.application.comment.service.dto.response.PostOfficialCommentResponse;
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.user.User;

import java.util.List;

@Getter
public class QnAPostDetailResponse extends PostDetailResDto {
    private List<PostOfficialCommentResponse> officialCommentList;
    private String department;
    private String college;

    @Builder
    public QnAPostDetailResponse(Long postId, String categoryName, String authorName, String title, String content, String createdAt, String lastEditedAt, Boolean isAuthor,
                                 List<PostOfficialCommentResponse> officialCommentList,
                                 List<String> canAuthority, String department, String college) {
        super(postId, categoryName, authorName, title, content, createdAt, lastEditedAt, isAuthor, canAuthority);
        this.officialCommentList = officialCommentList;
        this.department = department;
        this.college = college;
    }

    public static QnAPostDetailResponse of(Post post, Boolean isAuthor, User user, Member member,String categoryName,
                                           List<PostOfficialCommentResponse> officialCommentList) {
        return QnAPostDetailResponse.builder()
                .postId(post.getId())
                .authorName(user.getName())
                .categoryName(categoryName)
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .lastEditedAt(post.getLastEditedAt())
                .isAuthor(isAuthor)
                .officialCommentList(officialCommentList)
                .department(member.getMajorCode())
                .college(member.getMemberCode())
                .build();
    }
}
