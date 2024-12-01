package ussum.homepage.infra.jpa.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRightsDetailEntity is a Querydsl query type for RightsDetailEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRightsDetailEntity extends EntityPathBase<RightsDetailEntity> {

    private static final long serialVersionUID = 30014936L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRightsDetailEntity rightsDetailEntity = new QRightsDetailEntity("rightsDetailEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath major = createString("major");

    public final StringPath name = createString("name");

    public final EnumPath<RightsDetailEntity.PersonType> personType = createEnum("personType", RightsDetailEntity.PersonType.class);

    public final StringPath phoneNumber = createString("phoneNumber");

    public final QPostEntity postEntity;

    public final StringPath studentId = createString("studentId");

    public QRightsDetailEntity(String variable) {
        this(RightsDetailEntity.class, forVariable(variable), INITS);
    }

    public QRightsDetailEntity(Path<? extends RightsDetailEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRightsDetailEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRightsDetailEntity(PathMetadata metadata, PathInits inits) {
        this(RightsDetailEntity.class, metadata, inits);
    }

    public QRightsDetailEntity(Class<? extends RightsDetailEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.postEntity = inits.isInitialized("postEntity") ? new QPostEntity(forProperty("postEntity"), inits.get("postEntity")) : null;
    }

}

