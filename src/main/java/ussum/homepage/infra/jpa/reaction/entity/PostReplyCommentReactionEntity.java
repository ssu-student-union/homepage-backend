package ussum.homepage.infra.jpa.reaction.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ussum.homepage.infra.jpa.comment.entity.PostReplyCommentEntity;
import ussum.homepage.infra.jpa.postlike.entity.Reaction;
import ussum.homepage.infra.jpa.user.entity.UserEntity;

@Entity
@Table(name = "post_reply_comment_reaction")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostReplyCommentReactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_reply_comment_id")
    private PostReplyCommentEntity postReplyCommentEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Enumerated(EnumType.STRING)
    private Reaction reaction;

    public static PostReplyCommentReactionEntity of(Long id, PostReplyCommentEntity postReplyCommentEntity, UserEntity userEntity, Reaction reaction) {
        return new PostReplyCommentReactionEntity(id, postReplyCommentEntity, userEntity, reaction);
    }

    public static PostReplyCommentReactionEntity from(Long id) {
        return new PostReplyCommentReactionEntity(id, null, null, null);
    }
}
