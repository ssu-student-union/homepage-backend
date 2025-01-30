package ussum.homepage.infra.jpa.calendar_event.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;
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
    private Date startDate;

    @Column(nullable = false, name = "end_date")
    private Date endDate;

    @Column(nullable = false, name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false, name = "created_by")
    private Long createBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_at")
    private LocalDateTime updateAt;

    public static CalendarEventEntity from(Long id){
        return new CalendarEventEntity(id,null,null,null,null,null,null,null);
    }

    public static CalendarEventEntity of(Long id, String title, Date startDate, Date endDate, Category category, Long createBy, LocalDateTime createdAt, LocalDateTime updateAt) {
        return new CalendarEventEntity(id, title, startDate, endDate, category, createBy, createdAt, updateAt);
    }
}
