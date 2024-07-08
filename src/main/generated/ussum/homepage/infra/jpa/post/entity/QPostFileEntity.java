package ussum.homepage.infra.jpa.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostFileEntity is a Querydsl query type for PostFileEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostFileEntity extends EntityPathBase<PostFileEntity> {

    private static final long serialVersionUID = 721739404L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostFileEntity postFileEntity = new QPostFileEntity("postFileEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final QPostEntity postEntity;

    public final StringPath size = createString("size");

    public final StringPath url = createString("url");

    public QPostFileEntity(String variable) {
        this(PostFileEntity.class, forVariable(variable), INITS);
    }

    public QPostFileEntity(Path<? extends PostFileEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostFileEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostFileEntity(PathMetadata metadata, PathInits inits) {
        this(PostFileEntity.class, metadata, inits);
    }

    public QPostFileEntity(Class<? extends PostFileEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.postEntity = inits.isInitialized("postEntity") ? new QPostEntity(forProperty("postEntity"), inits.get("postEntity")) : null;
    }

}

