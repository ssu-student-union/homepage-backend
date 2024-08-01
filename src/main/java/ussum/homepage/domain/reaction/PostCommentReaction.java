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
    private String reaction;

    public static PostCommentReaction of(Long id,
                                         Long postCommentId,
                                         Long userId,
                                         String reaction) {
        return new PostCommentReaction(id, postCommentId, userId, reaction);
    }

    public void updateCommentReaction(PostCommentReaction newReaction) {
        this.reaction = newReaction.getReaction();
    }
}