package ussum.homepage.infra.jpa.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostEntity is a Querydsl query type for PostEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostEntity extends EntityPathBase<PostEntity> {

    private static final long serialVersionUID = -840034192L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostEntity postEntity = new QPostEntity("postEntity");

    public final ussum.homepage.infra.jpa.QBaseEntity _super = new ussum.homepage.infra.jpa.QBaseEntity(this);

    public final QBoardEntity boardEntity;

    public final QCategoryEntity categoryEntity;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> lastEditedAt = createDateTime("lastEditedAt", java.time.LocalDateTime.class);

    public final StringPath thumbnailImage = createString("thumbnailImage");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final ussum.homepage.infra.jpa.user.entity.QUserEntity userEntity;

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QPostEntity(String variable) {
        this(PostEntity.class, forVariable(variable), INITS);
    }

    public QPostEntity(Path<? extends PostEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostEntity(PathMetadata metadata, PathInits inits) {
        this(PostEntity.class, metadata, inits);
    }

    public QPostEntity(Class<? extends PostEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.boardEntity = inits.isInitialized("boardEntity") ? new QBoardEntity(forProperty("boardEntity")) : null;
        this.categoryEntity = inits.isInitialized("categoryEntity") ? new QCategoryEntity(forProperty("categoryEntity"), inits.get("categoryEntity")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new ussum.homepage.infra.jpa.user.entity.QUserEntity(forProperty("userEntity")) : null;
    }

}

