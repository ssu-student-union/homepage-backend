package ussum.homepage.infra.jpa.acl.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGlobalEntity is a Querydsl query type for GlobalEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGlobalEntity extends EntityPathBase<GlobalEntity> {

    private static final long serialVersionUID = -1896694339L;

    public static final QGlobalEntity globalEntity = new QGlobalEntity("globalEntity");

    public final EnumPath<Action> action = createEnum("action", Action.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<OrderType> orderType = createEnum("orderType", OrderType.class);

    public final EnumPath<Target> target = createEnum("target", Target.class);

    public final EnumPath<Type> type = createEnum("type", Type.class);

    public QGlobalEntity(String variable) {
        super(GlobalEntity.class, forVariable(variable));
    }

    public QGlobalEntity(Path<? extends GlobalEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGlobalEntity(PathMetadata metadata) {
        super(GlobalEntity.class, metadata);
    }

}

