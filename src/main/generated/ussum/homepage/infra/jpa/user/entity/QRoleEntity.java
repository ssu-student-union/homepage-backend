package ussum.homepage.infra.jpa.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoleEntity is a Querydsl query type for RoleEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoleEntity extends EntityPathBase<RoleEntity> {

    private static final long serialVersionUID = 1387087409L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoleEntity roleEntity = new QRoleEntity("roleEntity");

    public final ussum.homepage.infra.jpa.QBaseEntity _super = new ussum.homepage.infra.jpa.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<Role> role = createEnum("role", Role.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUserEntity userEntity;

    public QRoleEntity(String variable) {
        this(RoleEntity.class, forVariable(variable), INITS);
    }

    public QRoleEntity(Path<? extends RoleEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoleEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoleEntity(PathMetadata metadata, PathInits inits) {
        this(RoleEntity.class, metadata, inits);
    }

    public QRoleEntity(Class<? extends RoleEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userEntity = inits.isInitialized("userEntity") ? new QUserEntity(forProperty("userEntity")) : null;
    }

}

