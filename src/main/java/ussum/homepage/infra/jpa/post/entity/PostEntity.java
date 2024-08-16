package ussum.homepage.infra.jpa.post.entity;
import jakarta.persistence.*;
import lombok.*;
import ussum.homepage.domain.post.Category;
import ussum.homepage.infra.jpa.BaseEntity;
import ussum.homepage.infra.jpa.user.entity.UserEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "post")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private Integer viewCount;
    private String thumbnailImage;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private OngoingStatus ongoingStatus;

    private LocalDateTime lastEditedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity;

    public static PostEntity from(Long id){
        return new PostEntity(id, null, null, null, null, null, null, null, null, null, null);
    }

    public static PostEntity of(Long id, String title, String content, Integer viewCount, String thumbnailImage, Status status,
                                OngoingStatus ongoingStatus, LocalDateTime lastEditedAt, UserEntity user, BoardEntity board, CategoryEntity category) {
        return new PostEntity(id, title, content, viewCount, thumbnailImage, status, ongoingStatus, lastEditedAt, user, board, category);
    }

    public static void increaseViewCount(PostEntity post) {
        post.viewCount = post.getViewCount() == null ? 1 : post.viewCount + 1;
    }

    public static void updateLastEditedAt(PostEntity post) {
        post.lastEditedAt = LocalDateTime.now();
    }

    public void updateStatusAndCategoryCode(OngoingStatus newStatus, CategoryEntity updatedCategoryEntity) {
        this.ongoingStatus = newStatus;
        this.categoryEntity = updatedCategoryEntity;
    }
}
