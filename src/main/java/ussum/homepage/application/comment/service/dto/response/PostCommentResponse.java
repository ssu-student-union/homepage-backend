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
        List<PostReplyCommentResponse> postReplyComments
) {
    public static PostCommentResponse of(PostComment postComment, User user, String commentType, Integer likeCount, Boolean isAuthor, List<PostReplyCommentResponse> postReplyComments) {
        return new PostCommentResponse(
                postComment.getId(),
                user.getName(),
                user.getStudentId(),
                postComment.getContent(),
                commentType,
                postComment.getCreatedAt(),
                postComment.getLastEditedAt(),
                likeCount,
                isAuthor,
                postReplyComments);
    }
}
