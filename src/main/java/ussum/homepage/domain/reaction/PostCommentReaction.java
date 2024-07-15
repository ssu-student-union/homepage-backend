package ussum.homepage.domain.reaction;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCommentReaction {
    private Long id;
    private Long postCommentId;
    private String reactionType;

    public static PostCommentReaction of(Long id,
                                         Long postCommentId,
                                         String reactionType) {
        return new PostCommentReaction(id, postCommentId, reactionType);
    }
}
