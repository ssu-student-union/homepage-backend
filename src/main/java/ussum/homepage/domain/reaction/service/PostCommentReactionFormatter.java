package ussum.homepage.domain.reaction.service;

import ussum.homepage.application.reaction.service.dto.response.PostCommentReactionResponse;

public interface PostCommentReactionFormatter {
    PostCommentReactionResponse format(Long id, Long postCommentId, Long postId, Long userId, String type, String reaction);
}
