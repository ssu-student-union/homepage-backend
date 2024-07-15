package ussum.homepage.domain.reaction;

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCommentReaction {
    private Long id;
    private Long postCommentId;
    private Long userId;
    private String reactionType;

    public static PostCommentReaction of(Long id,
                                         Long postCommentId,
                                         Long userId,
                                         String reactionType) {
        return new PostCommentReaction(id, postCommentId, userId, reactionType);
    }
}
