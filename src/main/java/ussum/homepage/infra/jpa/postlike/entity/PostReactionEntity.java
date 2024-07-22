package ussum.homepage.infra.jpa.postlike.entity;
import jakarta.persistence.*;
import lombok.*;
import ussum.homepage.infra.jpa.post.entity.PostEntity;
import ussum.homepage.infra.jpa.user.entity.UserEntity;

@Entity
@Table(name = "post_reaction")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostReactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Reaction reaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public static PostReactionEntity of(Long id, PostEntity postEntity, UserEntity userEntity, Reaction reaction) {
        return new PostReactionEntity(id, postEntity, userEntity, reaction);
    }

    public PostReactionEntity(Long id, PostEntity postEntity, UserEntity userEntity, Reaction reaction) {
        this.id = id;
        this.postEntity = postEntity;
        this.userEntity = userEntity;
        this.reaction = reaction;
    }

    public PostReactionEntity from(Long id) {
        return new PostReactionEntity(id, null, null, null);
    }
}
