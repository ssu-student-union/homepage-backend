package ussum.homepage.infra.jpa.acl.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostAclEntity is a Querydsl query type for PostAclEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostAclEntity extends EntityPathBase<PostAclEntity> {

    private static final long serialVersionUID = -484045098L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostAclEntity postAclEntity = new QPostAclEntity("postAclEntity");

    public final EnumPath<Action> action = createEnum("action", Action.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<OrderType> orderType = createEnum("orderType", OrderType.class);

    public final ussum.homepage.infra.jpa.post.entity.QPostEntity postEntity;

    public final EnumPath<Target> target = createEnum("target", Target.class);

    public final EnumPath<Type> type = createEnum("type", Type.class);

    public QPostAclEntity(String variable) {
        this(PostAclEntity.class, forVariable(variable), INITS);
    }

    public QPostAclEntity(Path<? extends PostAclEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostAclEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostAclEntity(PathMetadata metadata, PathInits inits) {
        this(PostAclEntity.class, metadata, inits);
    }

    public QPostAclEntity(Class<? extends PostAclEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.postEntity = inits.isInitialized("postEntity") ? new ussum.homepage.infra.jpa.post.entity.QPostEntity(forProperty("postEntity"), inits.get("postEntity")) : null;
    }

}

