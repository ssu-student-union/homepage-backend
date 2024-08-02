package ussum.homepage.infra.jpa.csv_user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStudentCsvEntity is a Querydsl query type for StudentCsvEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudentCsvEntity extends EntityPathBase<StudentCsvEntity> {

    private static final long serialVersionUID = 1910442335L;

    public static final QStudentCsvEntity studentCsvEntity = new QStudentCsvEntity("studentCsvEntity");

    public final StringPath groupName = createString("groupName");

    public final StringPath major = createString("major");

    public final NumberPath<Long> STID = createNumber("STID", Long.class);

    public final StringPath studentEmail = createString("studentEmail");

    public final StringPath studentGroup = createString("studentGroup");

    public final NumberPath<Long> studentId = createNumber("studentId", Long.class);

    public final StringPath studentName = createString("studentName");

    public final StringPath studentStatus = createString("studentStatus");

    public QStudentCsvEntity(String variable) {
        super(StudentCsvEntity.class, forVariable(variable));
    }

    public QStudentCsvEntity(Path<? extends StudentCsvEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudentCsvEntity(PathMetadata metadata) {
        super(StudentCsvEntity.class, metadata);
    }

}

