package ussum.homepage.infra.csvBatch.csv.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStudentCsvData is a Querydsl query type for StudentCsvData
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudentCsvData extends EntityPathBase<StudentCsvData> {

    private static final long serialVersionUID = -2116096709L;

    public static final QStudentCsvData studentCsvData = new QStudentCsvData("studentCsvData");

    public final StringPath groupName = createString("groupName");

    public final StringPath major = createString("major");

    public final NumberPath<Long> STID = createNumber("STID", Long.class);

    public final StringPath studentEmail = createString("studentEmail");

    public final StringPath studentGroup = createString("studentGroup");

    public final NumberPath<Long> studentId = createNumber("studentId", Long.class);

    public final StringPath studentName = createString("studentName");

    public final StringPath studentStatus = createString("studentStatus");

    public QStudentCsvData(String variable) {
        super(StudentCsvData.class, forVariable(variable));
    }

    public QStudentCsvData(Path<? extends StudentCsvData> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudentCsvData(PathMetadata metadata) {
        super(StudentCsvData.class, metadata);
    }

}

