package ussum.homepage.application.post.service.dto.response.postDetail;

import lombok.Builder;
import lombok.Getter;
import ussum.homepage.application.comment.service.dto.response.PostOfficialCommentResponse;
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.user.User;

import java.util.List;
import java.util.Optional;

@Getter
public class QnAPostDetailResponse extends PostDetailResDto {
    private List<PostOfficialCommentResponse> officialCommentList;
    private String department;
    private String college;
    private String qnaTargetCode; // 질문 대상인 총학생 or 단과대 or 학과

    @Builder
    public QnAPostDetailResponse(Long postId, String categoryName, String authorName, String title, String content, String createdAt, String lastEditedAt, Boolean isAuthor,
                                 List<PostOfficialCommentResponse> officialCommentList,
                                 List<String> canAuthority, String department, String college, String qnaTargetCode) {
        super(postId, categoryName, authorName, title, content, createdAt, lastEditedAt, isAuthor, canAuthority);
        this.officialCommentList = officialCommentList;
        this.department = department;
        this.college = college;
        this.qnaTargetCode = qnaTargetCode;
    }

    public static QnAPostDetailResponse of(Post post, Boolean isAuthor, User user, Member member,String categoryName,
                                           List<PostOfficialCommentResponse> officialCommentList) {
        return QnAPostDetailResponse.builder()
                .postId(post.getId())
                .authorName(member.getIsAdmin() ? user.getName() : User.maskedName(user.getName()))
                .categoryName(categoryName)
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .lastEditedAt(post.getLastEditedAt())
                .isAuthor(isAuthor)
                .officialCommentList(officialCommentList)
                .department(member.getMajorCode())
                .college(member.getMemberCode())
                .qnaTargetCode(Optional.ofNullable(post.getQnaMajorCode()).orElse(post.getQnaMemberCode()))
                .build();
    }
}
