package ussum.homepage.application.comment.service.dto.response;

import lombok.Builder;
import ussum.homepage.domain.comment.PostReplyComment;
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.user.User;

import java.util.List;

public class PostReplyCommentResponse {
    public Long id;
    public String authorName;
    public String studentId;
    public String department;
    public String college;
    public String content;
    public String createdAt;
    public String lastEditedAt;
    public Integer likeCount;
    public Boolean isAuthor;
    public Boolean isLiked;
    public Boolean isDeleted;
//    public List<String> canAuthority;

    @Builder
    public PostReplyCommentResponse(Long id, String authorName, String studentId, String department, String college, String content, String createdAt, String lastEditedAt, Integer likeCount, Boolean isAuthor, Boolean isLiked, Boolean isDeleted/*, List<String> canAuthority*/) {
        this.id = id;
        this.authorName = authorName;
        this.studentId = studentId;
        this.department = department;
        this.college = college;
        this.content = content;
        this.createdAt = createdAt;
        this.lastEditedAt = lastEditedAt;
        this.likeCount = likeCount;
        this.isAuthor = isAuthor;
        this.isLiked = isLiked;
        this.isDeleted = isDeleted;
//        this.canAuthority = canAuthority;
    }

    public static PostReplyCommentResponse of(PostReplyComment postReplyComment, User user, Member member, Integer likeCount, Boolean isAuthor, Boolean isLiked) {
        String studentId = user.getStudentId();
        String content = postReplyComment.getContent();
        if (postReplyComment.getIsDeleted().equals(true)) {
            studentId = "삭제된 사용자입니다.";
            content = "삭제된 댓글입니다.";
        }
        return PostReplyCommentResponse.builder()
                .id(postReplyComment.getId())
                .authorName(member.getIsAdmin() ? user.getName() : User.maskedName(user.getName()))
                .studentId(studentId)
                .department(member.getMajorCode())
                .college(member.getMemberCode())
                .content(content)
                .createdAt(postReplyComment.getCreatedAt())
                .lastEditedAt(postReplyComment.getLastEditedAt())
                .likeCount(likeCount)
                .isAuthor(isAuthor)
                .isLiked(isLiked)
                .isDeleted(postReplyComment.getIsDeleted())
                .build();
    }

//    public void canAuthority(List<String> canAuthorityList) {
//        this.canAuthority = canAuthorityList;
//    }
}
