package ussum.homepage.infra.jpa.comment;

import org.springframework.stereotype.Component;
import ussum.homepage.domain.comment.PostReplyComment;
import ussum.homepage.infra.jpa.comment.entity.PostCommentEntity;
import ussum.homepage.infra.jpa.comment.entity.PostReplyCommentEntity;
import ussum.homepage.infra.jpa.user.entity.UserEntity;
import ussum.homepage.infra.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class PostReplyCommentMapper {
    public PostReplyComment toDomain(PostReplyCommentEntity postReplyCommentEntity) {
        return PostReplyComment.of(
                postReplyCommentEntity.getId(),
                postReplyCommentEntity.getContent(),
                postReplyCommentEntity.getPostCommentEntity().getId(),
                postReplyCommentEntity.getUserEntity().getId(),
                String.valueOf(postReplyCommentEntity.getCreatedAt()),
                String.valueOf(postReplyCommentEntity.getLastEditedAt()),
                postReplyCommentEntity.getIsDeleted(),
                String.valueOf(postReplyCommentEntity.getDeletedAt())
        );
    }

    public PostReplyCommentEntity toEntity(PostReplyComment postReplyComment) {
        LocalDateTime lastEditedAt = DateUtils.parseHourMinSecFromCustomString(postReplyComment.getLastEditedAt());
        LocalDateTime deletedEditedAt = DateUtils.parseHourMinSecFromCustomString(postReplyComment.getDeletedAt());

        return PostReplyCommentEntity.of(
                postReplyComment.getId(),
                postReplyComment.getContent(),
                PostCommentEntity.from(postReplyComment.getCommentId()),
                UserEntity.from(postReplyComment.getUserId()),
                lastEditedAt,
                postReplyComment.getIsDeleted(),
                deletedEditedAt
        );
    }

}
