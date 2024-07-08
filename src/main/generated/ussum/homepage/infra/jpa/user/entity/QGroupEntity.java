package ussum.homepage.infra.jpa.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGroupEntity is a Querydsl query type for GroupEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroupEntity extends EntityPathBase<GroupEntity> {

    private static final long serialVersionUID = 1363790858L;

    public static final QGroupEntity groupEntity = new QGroupEntity("groupEntity");

    public final ussum.homepage.infra.jpa.QBaseEntity _super = new ussum.homepage.infra.jpa.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<MajorCode> majorCode = createEnum("majorCode", MajorCode.class);

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QGroupEntity(String variable) {
        super(GroupEntity.class, forVariable(variable));
    }

    public QGroupEntity(Path<? extends GroupEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGroupEntity(PathMetadata metadata) {
        super(GroupEntity.class, metadata);
    }

}

