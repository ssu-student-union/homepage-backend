package ussum.homepage.infra.jpa.comment;

import org.springframework.stereotype.Component;
import ussum.homepage.domain.comment.PostComment;
import ussum.homepage.infra.jpa.comment.entity.CommentType;
import ussum.homepage.infra.jpa.comment.entity.PostCommentEntity;
import ussum.homepage.infra.jpa.post.entity.PostEntity;
import ussum.homepage.infra.jpa.user.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class PostCommentMapper {
    public PostComment toDomain(PostCommentEntity postCommentEntity) {
        return PostComment.of(
                postCommentEntity.getId(),
                postCommentEntity.getContent(),
                postCommentEntity.getPostEntity().getId(),
                postCommentEntity.getUserEntity().getId(),
                String.valueOf(postCommentEntity.getCommentType()),
                String.valueOf(postCommentEntity.getCreatedAt()),
                String.valueOf(postCommentEntity.getLastEditedAt())
        );
    }
    public PostCommentEntity toEntity(PostComment postComment){
        LocalDateTime lastEditedAt = Optional.ofNullable(postComment.getLastEditedAt())
                .filter(date -> !"null".equals(date))
                .map(LocalDateTime::parse)
                .orElse(null);

        return PostCommentEntity.of(
                postComment.getId(),
                postComment.getContent(),
                PostEntity.from(postComment.getPostId()),
                UserEntity.from(postComment.getUserId()),
                CommentType.getEnumCommentTypeFromStringCommentType(postComment.getCommentType()),
                lastEditedAt
        );
    }
}
