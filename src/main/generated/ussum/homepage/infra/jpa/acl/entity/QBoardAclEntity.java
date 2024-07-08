package ussum.homepage.infra.jpa.acl.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardAclEntity is a Querydsl query type for BoardAclEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardAclEntity extends EntityPathBase<BoardAclEntity> {

    private static final long serialVersionUID = 1242137438L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardAclEntity boardAclEntity = new QBoardAclEntity("boardAclEntity");

    public final EnumPath<Action> action = createEnum("action", Action.class);

    public final ussum.homepage.infra.jpa.post.entity.QBoardEntity boardEntity;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<OrderType> orderType = createEnum("orderType", OrderType.class);

    public final EnumPath<Target> target = createEnum("target", Target.class);

    public final EnumPath<Type> type = createEnum("type", Type.class);

    public QBoardAclEntity(String variable) {
        this(BoardAclEntity.class, forVariable(variable), INITS);
    }

    public QBoardAclEntity(Path<? extends BoardAclEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardAclEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardAclEntity(PathMetadata metadata, PathInits inits) {
        this(BoardAclEntity.class, metadata, inits);
    }

    public QBoardAclEntity(Class<? extends BoardAclEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.boardEntity = inits.isInitialized("boardEntity") ? new ussum.homepage.infra.jpa.post.entity.QBoardEntity(forProperty("boardEntity")) : null;
    }

}

