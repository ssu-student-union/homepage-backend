package ussum.homepage.infra.jpa.reaction.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostCommentReactionEntity is a Querydsl query type for PostCommentReactionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostCommentReactionEntity extends EntityPathBase<PostCommentReactionEntity> {

    private static final long serialVersionUID = 653880085L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostCommentReactionEntity postCommentReactionEntity = new QPostCommentReactionEntity("postCommentReactionEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ussum.homepage.infra.jpa.comment.entity.QPostCommentEntity postCommentEntity;

    public final EnumPath<ussum.homepage.infra.jpa.postlike.entity.Reaction> reaction = createEnum("reaction", ussum.homepage.infra.jpa.postlike.entity.Reaction.class);

    public final ussum.homepage.infra.jpa.user.entity.QUserEntity userEntity;

    public QPostCommentReactionEntity(String variable) {
        this(PostCommentReactionEntity.class, forVariable(variable), INITS);
    }

    public QPostCommentReactionEntity(Path<? extends PostCommentReactionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostCommentReactionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostCommentReactionEntity(PathMetadata metadata, PathInits inits) {
        this(PostCommentReactionEntity.class, metadata, inits);
    }

    public QPostCommentReactionEntity(Class<? extends PostCommentReactionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.postCommentEntity = inits.isInitialized("postCommentEntity") ? new ussum.homepage.infra.jpa.comment.entity.QPostCommentEntity(forProperty("postCommentEntity"), inits.get("postCommentEntity")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new ussum.homepage.infra.jpa.user.entity.QUserEntity(forProperty("userEntity")) : null;
    }

}

