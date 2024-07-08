package ussum.homepage.infra.jpa.comment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostCommentEntity is a Querydsl query type for PostCommentEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostCommentEntity extends EntityPathBase<PostCommentEntity> {

    private static final long serialVersionUID = -1647472586L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostCommentEntity postCommentEntity = new QPostCommentEntity("postCommentEntity");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> lastEditedAt = createDateTime("lastEditedAt", java.time.LocalDateTime.class);

    public final ussum.homepage.infra.jpa.post.entity.QPostEntity postEntity;

    public final ussum.homepage.infra.jpa.user.entity.QUserEntity userEntity;

    public QPostCommentEntity(String variable) {
        this(PostCommentEntity.class, forVariable(variable), INITS);
    }

    public QPostCommentEntity(Path<? extends PostCommentEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostCommentEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostCommentEntity(PathMetadata metadata, PathInits inits) {
        this(PostCommentEntity.class, metadata, inits);
    }

    public QPostCommentEntity(Class<? extends PostCommentEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.postEntity = inits.isInitialized("postEntity") ? new ussum.homepage.infra.jpa.post.entity.QPostEntity(forProperty("postEntity"), inits.get("postEntity")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new ussum.homepage.infra.jpa.user.entity.QUserEntity(forProperty("userEntity")) : null;
    }

}

