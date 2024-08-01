package ussum.homepage.infra.jpa.comment.entity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import ussum.homepage.domain.comment.PostComment;
import ussum.homepage.infra.jpa.BaseEntity;
import ussum.homepage.infra.jpa.post.entity.PostEntity;
import ussum.homepage.infra.jpa.user.entity.UserEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "post_comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
    @Enumerated(EnumType.STRING)
    private CommentType type;

    @LastModifiedDate
    private LocalDateTime lastEditedAt;

    public PostCommentEntity(Long id, String content, PostEntity postEntity, UserEntity userEntity, LocalDateTime lastEditedAt) {
        this.id = id;
        this.content = content;
        this.postEntity = postEntity;
        this.userEntity = userEntity;
        this.lastEditedAt = lastEditedAt;
    }

    public static PostCommentEntity of(Long id, String content, PostEntity postEntity, UserEntity userEntity, LocalDateTime lastEditedAt){
        return new PostCommentEntity(id, content, postEntity, userEntity, lastEditedAt);
    }

    public static PostCommentEntity from(Long id) {
        return new PostCommentEntity(id, null, null, null, null);
    }

}
