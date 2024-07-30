package ussum.homepage.domain.postlike;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ussum.homepage.domain.reaction.PostCommentReaction;
import ussum.homepage.infra.jpa.post.entity.PostEntity;
import ussum.homepage.infra.jpa.postlike.entity.Reaction;
import ussum.homepage.infra.jpa.user.entity.UserEntity;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostReaction {
    private Long id;
    private String reaction;
    private Long postId;
    private Long userId;


    public static PostReaction of(Long id,
                                  String reaction,
                                         Long postId,
                                         Long userId) {
        return new PostReaction(id, reaction, postId, userId);
    }
    public void updateReaction(PostReaction newReaction) {
        this.reaction = newReaction.getReaction();

    }
}
