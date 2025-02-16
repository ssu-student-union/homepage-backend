package ussum.homepage.infra.jpa.calendar_event.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ussum.homepage.infra.jpa.post.entity.Category;

@Entity
@Table(name = "calendar_event")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = false, name = "start_date")
    private LocalDate startDate;

    @Column(nullable = false, name = "end_date")
    private LocalDate endDate;

    @Column(nullable = false, name = "category")
    @Enumerated(EnumType.STRING)
    private CalendarCategory calendarCategory;

    @Column(nullable = false, name = "created_by")
    private Long createBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static CalendarEventEntity from(Long id){
        return new CalendarEventEntity(id,null,null,null,null,null,null,null);
    }

    public static CalendarEventEntity of(Long id, String title, LocalDate startDate, LocalDate endDate, CalendarCategory calendarCategory, Long createBy, LocalDateTime createdAt, LocalDateTime updateAt) {
        return new CalendarEventEntity(id, title, startDate, endDate, calendarCategory, createBy, createdAt, updateAt);
    }
}
