package ussum.homepage.infra.jpa.postlike.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostReactionEntity is a Querydsl query type for PostReactionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostReactionEntity extends EntityPathBase<PostReactionEntity> {

    private static final long serialVersionUID = -699090992L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostReactionEntity postReactionEntity = new QPostReactionEntity("postReactionEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ussum.homepage.infra.jpa.post.entity.QPostEntity postEntity;

    public final EnumPath<Reaction> reaction = createEnum("reaction", Reaction.class);

    public final ussum.homepage.infra.jpa.user.entity.QUserEntity userEntity;

    public QPostReactionEntity(String variable) {
        this(PostReactionEntity.class, forVariable(variable), INITS);
    }

    public QPostReactionEntity(Path<? extends PostReactionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostReactionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostReactionEntity(PathMetadata metadata, PathInits inits) {
        this(PostReactionEntity.class, metadata, inits);
    }

    public QPostReactionEntity(Class<? extends PostReactionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.postEntity = inits.isInitialized("postEntity") ? new ussum.homepage.infra.jpa.post.entity.QPostEntity(forProperty("postEntity"), inits.get("postEntity")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new ussum.homepage.infra.jpa.user.entity.QUserEntity(forProperty("userEntity")) : null;
    }

}

