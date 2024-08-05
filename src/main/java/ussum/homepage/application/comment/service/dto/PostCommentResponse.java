package ussum.homepage.application.comment.service.dto;

import ussum.homepage.application.post.service.dto.response.PostResponse;
import ussum.homepage.application.user.service.dto.response.UserResponse;
import ussum.homepage.domain.comment.PostComment;

public record PostCommentResponse(
        Long id,
        String authorName,
        String content,
        String commentType,
        String lastEditedAt,
        Integer likeCount

) {
    public static PostCommentResponse of(PostComment postComment, String authorName, String commentType, Integer likeCount) {
        return new PostCommentResponse(
                postComment.getId(),
                authorName,
                postComment.getContent(),
                commentType,
                postComment.getLastEditedAt(),
                likeCount);
    }
}
