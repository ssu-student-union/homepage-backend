package ussum.homepage.application.comment.service.dto.response;

import ussum.homepage.domain.comment.PostComment;
import ussum.homepage.domain.user.User;

import java.util.List;

public record PostCommentResponse(
        Long id,
        String authorName,
        String studentId,
        String content,
        String commentType,
        String createdAt,
        String lastEditedAt,
        Integer likeCount,
        Boolean isAuthor,
        Boolean isLiked,
        Boolean isDeleted,
        List<PostReplyCommentResponse> postReplyComments
) {
    public static PostCommentResponse of(PostComment postComment, User user, String commentType, Integer likeCount, Boolean isAuthor, Boolean isLiked, List<PostReplyCommentResponse> postReplyComments) {
        String studentId = user.getStudentId();
        String content = postComment.getContent();
        if (postComment.getIsDeleted().equals(true)) {
            studentId = "삭제된 사용자입니다.";
            content = "삭제된 댓글입니다.";
        }

        return new PostCommentResponse(
                postComment.getId(),
                user.getName(),
                studentId,
                content,
                commentType,
                postComment.getCreatedAt(),
                postComment.getLastEditedAt(),
                likeCount,
                isAuthor,
                isLiked,
                postComment.getIsDeleted(),
                postReplyComments);
    }
}
