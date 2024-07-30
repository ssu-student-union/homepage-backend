package ussum.homepage.infra.jpa.reaction.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ussum.homepage.infra.jpa.comment.entity.PostCommentEntity;
import ussum.homepage.infra.jpa.postlike.entity.Reaction;
import ussum.homepage.infra.jpa.user.entity.UserEntity;

@Entity
@Table(name = "post_comment_reaction")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostCommentReactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_comment_id")
    private PostCommentEntity postCommentEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Enumerated(EnumType.STRING)
    private Reaction reaction;

    public static PostCommentReactionEntity of(Long id, PostCommentEntity postCommentEntity,UserEntity userEntity, Reaction reaction) {
        return new PostCommentReactionEntity(id, postCommentEntity, userEntity, reaction);
    }

    public static PostCommentReactionEntity from(Long id) {
        return new PostCommentReactionEntity(id, null, null, null);
    }

}
