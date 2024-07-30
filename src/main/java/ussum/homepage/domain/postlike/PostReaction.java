package ussum.homepage.domain.postlike;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ussum.homepage.infra.jpa.post.entity.PostEntity;
import ussum.homepage.infra.jpa.postlike.entity.Reaction;
import ussum.homepage.infra.jpa.user.entity.UserEntity;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostReaction {
    private Long id;
    private Long postId;
    private Long userId;
    private String reactionType;

    public static PostReaction of(Long id,
                                  Long postId,
                                  Long userId,
                                  String reactionType) {
        return new PostReaction(id, postId, userId, reactionType);
    }
}
