package ussum.homepage.domain.reaction;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostReplyCommentReaction {
    private Long id;
    private Long postReplyCommentId;
    private Long userId;
    private String reaction;

    public static PostReplyCommentReaction of(Long id, Long postReplyCommentId, Long userId, String reaction) {
        return new PostReplyCommentReaction(id, postReplyCommentId, userId, reaction);
    }

    public void updateReplyCommentReaction(PostReplyCommentReaction newReaction) {
        this.reaction = newReaction.getReaction();
    }

}
