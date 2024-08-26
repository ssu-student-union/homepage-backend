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
public class PostCommentEntity extends BaseEntity {
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
    private CommentType commentType;

    private LocalDateTime lastEditedAt;

    private Boolean isDeleted;

    private LocalDateTime deletedAt;

    public PostCommentEntity(Long id, String content, PostEntity postEntity, UserEntity userEntity, CommentType commentType, LocalDateTime lastEditedAt,
                             Boolean isDeleted, LocalDateTime deletedAt) {
        this.id = id;
        this.content = content;
        this.postEntity = postEntity;
        this.userEntity = userEntity;
        this.commentType = commentType;
        this.lastEditedAt = lastEditedAt;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
    }

    public static PostCommentEntity of(Long id, String content, PostEntity postEntity, UserEntity userEntity, CommentType commentType, LocalDateTime lastEditedAt,
                                       Boolean isDeleted, LocalDateTime deletedAt) {
        return new PostCommentEntity(id, content, postEntity, userEntity, commentType, lastEditedAt, isDeleted, deletedAt);
    }

    public static PostCommentEntity from(Long id) {
        return new PostCommentEntity(id, null, null, null, null, null, null, null);
    }

//    public static void updateLastEditedAt(PostCommentEntity postComment) {
//        postComment.lastEditedAt = LocalDateTime.now();
//    }

    public static void updateDeletedAt(PostCommentEntity postComment) {
        postComment.deletedAt = LocalDateTime.now();
        postComment.isDeleted = true;
    }
}
