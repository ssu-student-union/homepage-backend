package ussum.homepage.infra.jpa.reaction.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostReplyCommentReactionEntity is a Querydsl query type for PostReplyCommentReactionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostReplyCommentReactionEntity extends EntityPathBase<PostReplyCommentReactionEntity> {

    private static final long serialVersionUID = -139667017L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostReplyCommentReactionEntity postReplyCommentReactionEntity = new QPostReplyCommentReactionEntity("postReplyCommentReactionEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ussum.homepage.infra.jpa.comment.entity.QPostReplyCommentEntity postReplyCommentEntity;

    public final EnumPath<ussum.homepage.infra.jpa.postlike.entity.Reaction> reaction = createEnum("reaction", ussum.homepage.infra.jpa.postlike.entity.Reaction.class);

    public final ussum.homepage.infra.jpa.user.entity.QUserEntity userEntity;

    public QPostReplyCommentReactionEntity(String variable) {
        this(PostReplyCommentReactionEntity.class, forVariable(variable), INITS);
    }

    public QPostReplyCommentReactionEntity(Path<? extends PostReplyCommentReactionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostReplyCommentReactionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostReplyCommentReactionEntity(PathMetadata metadata, PathInits inits) {
        this(PostReplyCommentReactionEntity.class, metadata, inits);
    }

    public QPostReplyCommentReactionEntity(Class<? extends PostReplyCommentReactionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.postReplyCommentEntity = inits.isInitialized("postReplyCommentEntity") ? new ussum.homepage.infra.jpa.comment.entity.QPostReplyCommentEntity(forProperty("postReplyCommentEntity"), inits.get("postReplyCommentEntity")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new ussum.homepage.infra.jpa.user.entity.QUserEntity(forProperty("userEntity")) : null;
    }

}

