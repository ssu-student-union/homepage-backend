package ussum.homepage.infra.jpa.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberEntity is a Querydsl query type for MemberEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberEntity extends EntityPathBase<MemberEntity> {

    private static final long serialVersionUID = 475690837L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberEntity memberEntity = new QMemberEntity("memberEntity");

    public final ussum.homepage.infra.jpa.QBaseEntity _super = new ussum.homepage.infra.jpa.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QGroupEntity groupEntity;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isAdmin = createBoolean("isAdmin");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUserEntity userEntity;

    public QMemberEntity(String variable) {
        this(MemberEntity.class, forVariable(variable), INITS);
    }

    public QMemberEntity(Path<? extends MemberEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberEntity(PathMetadata metadata, PathInits inits) {
        this(MemberEntity.class, metadata, inits);
    }

    public QMemberEntity(Class<? extends MemberEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.groupEntity = inits.isInitialized("groupEntity") ? new QGroupEntity(forProperty("groupEntity")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new QUserEntity(forProperty("userEntity")) : null;
    }

}

