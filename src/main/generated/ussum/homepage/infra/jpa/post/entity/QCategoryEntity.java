package ussum.homepage.infra.jpa.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategoryEntity is a Querydsl query type for CategoryEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategoryEntity extends EntityPathBase<CategoryEntity> {

    private static final long serialVersionUID = 2117619662L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCategoryEntity categoryEntity = new QCategoryEntity("categoryEntity");

    public final ussum.homepage.infra.jpa.QBaseEntity _super = new ussum.homepage.infra.jpa.QBaseEntity(this);

    public final QBoardEntity boardEntity;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<ussum.homepage.infra.jpa.user.entity.MajorCode> majorCode = createEnum("majorCode", ussum.homepage.infra.jpa.user.entity.MajorCode.class);

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCategoryEntity(String variable) {
        this(CategoryEntity.class, forVariable(variable), INITS);
    }

    public QCategoryEntity(Path<? extends CategoryEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCategoryEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCategoryEntity(PathMetadata metadata, PathInits inits) {
        this(CategoryEntity.class, metadata, inits);
    }

    public QCategoryEntity(Class<? extends CategoryEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.boardEntity = inits.isInitialized("boardEntity") ? new QBoardEntity(forProperty("boardEntity")) : null;
    }

}

