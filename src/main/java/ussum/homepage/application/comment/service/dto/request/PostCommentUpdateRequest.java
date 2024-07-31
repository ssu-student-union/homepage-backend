package ussum.homepage.application.comment.service.dto.request;

import ussum.homepage.domain.comment.PostComment;

import java.time.LocalDateTime;

public record PostCommentUpdateRequest(
        String content
) {
    public PostComment toDomain(Long commentId, Long postId, Long userId){
        return PostComment.of(
                commentId,
                content,
                postId,
                userId,
                String.valueOf(LocalDateTime.now())
        );
    }

}
