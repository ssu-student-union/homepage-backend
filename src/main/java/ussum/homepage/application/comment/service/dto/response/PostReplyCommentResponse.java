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
        Boolean isAuthor
) {
    public static PostReplyCommentResponse of(PostReplyComment postReplyComment, User user, Integer likeCount, Boolean isAuthor) {
        return new PostReplyCommentResponse(
                postReplyComment.getId(),
                user.getName(),
                user.getStudentId(),
                postReplyComment.getContent(),
                postReplyComment.getCreatedAt(),
                postReplyComment.getLastEditedAt(),
                likeCount,
                isAuthor
        );
    }
}
