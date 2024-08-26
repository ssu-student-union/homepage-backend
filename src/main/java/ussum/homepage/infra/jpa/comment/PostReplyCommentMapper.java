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
                postReplyCommentEntity.getCreatedAt(),
                postReplyCommentEntity.getUpdatedAt(),
                postReplyCommentEntity.getLastEditedAt(),
                postReplyCommentEntity.getIsDeleted(),
                postReplyCommentEntity.getDeletedAt()
        );
    }

    public PostReplyCommentEntity toEntity(PostReplyComment postReplyComment) {
        LocalDateTime lastEditedAt = DateUtils.parseHourMinSecFromCustomString(postReplyComment.getLastEditedAt());
        LocalDateTime deletedEditedAt = DateUtils.parseHourMinSecFromCustomString(postReplyComment.getDeletedAt());
        System.out.println("postReplyComment.getUserId() = " + postReplyComment.getUserId());

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
