package ussum.homepage.infra.jpa.comment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import ussum.homepage.infra.jpa.BaseEntity;
import ussum.homepage.infra.jpa.user.entity.UserEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "post_reply_comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostReplyCommentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_comment_id")
    private PostCommentEntity postCommentEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    private LocalDateTime lastEditedAt;

    private Boolean isDeleted;

    private LocalDateTime deletedAt;

    public PostReplyCommentEntity(Long id, String content, PostCommentEntity postCommentEntity, UserEntity userEntity, LocalDateTime lastEditedAt,
                                  Boolean isDeleted, LocalDateTime deletedAt) {
        this.id = id;
        this.content = content;
        this.postCommentEntity = postCommentEntity;
        this.userEntity = userEntity;
        this.lastEditedAt = lastEditedAt;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
    }

    public static PostReplyCommentEntity of(Long id, String content, PostCommentEntity postCommentEntity, UserEntity userEntity, LocalDateTime lastEditedAt,
                                            Boolean isDeleted, LocalDateTime deletedAt) {
        return new PostReplyCommentEntity(id, content, postCommentEntity, userEntity, lastEditedAt, isDeleted, deletedAt);
    }

    public static PostReplyCommentEntity from(Long id) {
        return new PostReplyCommentEntity(id, null, null, null, null, null, null);
    }

    public static void updateLastEditedAt(PostReplyCommentEntity postReplyComment) {
        postReplyComment.lastEditedAt = LocalDateTime.now();
    }

    public static void updateDeletedAt(PostReplyCommentEntity postReplyComment) {
        postReplyComment.deletedAt = LocalDateTime.now();
    }

}
