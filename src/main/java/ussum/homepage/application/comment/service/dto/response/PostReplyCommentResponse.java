package ussum.homepage.application.comment.service.dto.response;

import ussum.homepage.domain.comment.PostReplyComment;
import ussum.homepage.domain.user.User;

public record PostReplyCommentResponse(
        Long id,
        String authorName,
        String studentId,
        String content,
        String createdAt,
        String lastEditedAt,
        Integer likeCount,
        Boolean isAuthor,
        Boolean isLiked,
        Boolean isDeleted
) {
    public static PostReplyCommentResponse of(PostReplyComment postReplyComment, User user, Integer likeCount, Boolean isAuthor, Boolean isLiked) {
        String studentId = user.getStudentId();
        String content = postReplyComment.getContent();
        if (postReplyComment.getIsDeleted().equals(true)) {
            studentId = "삭제된 사용자입니다.";
            content = "삭제된 댓글입니다.";
        }
        return new PostReplyCommentResponse(
                postReplyComment.getId(),
                user.getName(),
                studentId,
                content,
                postReplyComment.getCreatedAt(),
                postReplyComment.getLastEditedAt(),
                likeCount,
                isAuthor,
                isLiked,
                postReplyComment.getIsDeleted()
        );
    }
}
