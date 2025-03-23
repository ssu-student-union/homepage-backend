package ussum.homepage.infra.jpa.calendar_event.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCalendarEventEntity is a Querydsl query type for CalendarEventEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCalendarEventEntity extends EntityPathBase<CalendarEventEntity> {

    private static final long serialVersionUID = -531700103L;

    public static final QCalendarEventEntity calendarEventEntity = new QCalendarEventEntity("calendarEventEntity");

    public final EnumPath<CalendarCategory> calendarCategory = createEnum("calendarCategory", CalendarCategory.class);

    public final NumberPath<Long> createBy = createNumber("createBy", Long.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QCalendarEventEntity(String variable) {
        super(CalendarEventEntity.class, forVariable(variable));
    }

    public QCalendarEventEntity(Path<? extends CalendarEventEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCalendarEventEntity(PathMetadata metadata) {
        super(CalendarEventEntity.class, metadata);
    }

}

