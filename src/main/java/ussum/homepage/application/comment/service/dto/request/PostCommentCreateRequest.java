package ussum.homepage.application.comment.service.dto.request;

import ussum.homepage.domain.comment.PostComment;

public record PostCommentCreateRequest(
        String content
) {
    public PostComment toDomain(Long userId, Long postId){
        return PostComment.of(
                null,
                content,
                postId,
                userId,
                null,
                null,
                null
        );
    }
}
