package ussum.homepage.infra.jpa.comment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostReplyCommentEntity is a Querydsl query type for PostReplyCommentEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostReplyCommentEntity extends EntityPathBase<PostReplyCommentEntity> {

    private static final long serialVersionUID = 2085460932L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostReplyCommentEntity postReplyCommentEntity = new QPostReplyCommentEntity("postReplyCommentEntity");

    public final ussum.homepage.infra.jpa.QBaseEntity _super = new ussum.homepage.infra.jpa.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final DateTimePath<java.time.LocalDateTime> lastEditedAt = createDateTime("lastEditedAt", java.time.LocalDateTime.class);

    public final QPostCommentEntity postCommentEntity;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final ussum.homepage.infra.jpa.user.entity.QUserEntity userEntity;

    public QPostReplyCommentEntity(String variable) {
        this(PostReplyCommentEntity.class, forVariable(variable), INITS);
    }

    public QPostReplyCommentEntity(Path<? extends PostReplyCommentEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostReplyCommentEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostReplyCommentEntity(PathMetadata metadata, PathInits inits) {
        this(PostReplyCommentEntity.class, metadata, inits);
    }

    public QPostReplyCommentEntity(Class<? extends PostReplyCommentEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.postCommentEntity = inits.isInitialized("postCommentEntity") ? new QPostCommentEntity(forProperty("postCommentEntity"), inits.get("postCommentEntity")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new ussum.homepage.infra.jpa.user.entity.QUserEntity(forProperty("userEntity")) : null;
    }

}

