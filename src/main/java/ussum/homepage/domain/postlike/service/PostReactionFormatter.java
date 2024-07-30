package ussum.homepage.domain.postlike.service;

import ussum.homepage.application.reaction.service.dto.response.PostReactionResponse;

public interface PostReactionFormatter {
    PostReactionResponse format(Long id,Long postId, Long userId, String reaction);
}
