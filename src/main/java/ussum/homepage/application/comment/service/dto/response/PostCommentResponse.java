package ussum.homepage.application.comment.service.dto.response;

import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.comment.PostComment;
import ussum.homepage.domain.user.User;

import java.util.List;

@Getter
public class PostCommentResponse {
    public Long id;
    public String authorName;
    public String studentId;
    public String content;
    public String commentType;
    public String createdAt;
    public String lastEditedAt;
    public Integer likeCount;
    public Boolean isAuthor;
    public Boolean isLiked;
    public Boolean isDeleted;
    public List<PostReplyCommentResponse> postReplyComments;
    public List<String> canAuthority;

    @Builder
    public PostCommentResponse(Long id, String authorName, String studentId, String content, String commentType, String createdAt, String lastEditedAt, Integer likeCount, Boolean isAuthor, Boolean isLiked, Boolean isDeleted, List<PostReplyCommentResponse> postReplyComments, List<String> canAuthority) {
        this.id = id;
        this.authorName = authorName;
        this.studentId = studentId;
        this.content = content;
        this.commentType = commentType;
        this.createdAt = createdAt;
        this.lastEditedAt = lastEditedAt;
        this.likeCount = likeCount;
        this.isAuthor = isAuthor;
        this.isLiked = isLiked;
        this.isDeleted = isDeleted;
        this.postReplyComments = postReplyComments;
        this.canAuthority = canAuthority;
    }

    public static PostCommentResponse of(PostComment postComment, User user, String commentType, Integer likeCount, Boolean isAuthor, Boolean isLiked, List<PostReplyCommentResponse> postReplyComments) {
        String studentId = user.getStudentId();
        String content = postComment.getContent();
        if (postComment.getIsDeleted().equals(true)) {
            studentId = "삭제된 사용자입니다.";
            content = "삭제된 댓글입니다.";
        }

        return PostCommentResponse.builder()
                .id(postComment.getId())
                .authorName(user.getName())
                .studentId(studentId)
                .content(content)
                .commentType(commentType)
                .createdAt(postComment.getCreatedAt())
                .lastEditedAt(postComment.getLastEditedAt())
                .likeCount(likeCount)
                .isLiked(isLiked)
                .isDeleted(postComment.getIsDeleted())
                .postReplyComments(postReplyComments)
                .build();
    }

    public void canAuthority(List<String> canAuthorityList) {
        this.canAuthority = canAuthorityList;
    }

}
