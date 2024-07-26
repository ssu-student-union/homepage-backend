package ussum.homepage.domain.postlike.service.formatter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.application.reaction.service.dto.response.PostReactionResponse;
import ussum.homepage.domain.post.service.PostFormatter;
import ussum.homepage.domain.user.service.formatter.UserFormatter;

@Service
@RequiredArgsConstructor
public class PostReactionFormatter implements ussum.homepage.domain.postlike.service.PostReactionFormatter {
    private final UserFormatter userFormatter;
    private final PostFormatter postFormatter;

    @Override
    public PostReactionResponse format(Long id, Long postId, Long userId, String reaction) {
        return PostReactionResponse.of(
                id,
                postFormatter.format(postId),
                userFormatter.format(userId),
                reaction
        );
    }
}
