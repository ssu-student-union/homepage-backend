package ussum.homepage.infra.jpa.post.entity;
import jakarta.persistence.*;
import lombok.*;
import ussum.homepage.domain.member.Member;
import ussum.homepage.infra.jpa.BaseEntity;
import ussum.homepage.infra.jpa.member.entity.MajorCode;
import ussum.homepage.infra.jpa.member.entity.MemberCode;
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

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private Integer viewCount;
    private String thumbnailImage;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime lastEditedAt;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private SuggestionTarget suggestionTarget;

    @Enumerated(EnumType.STRING)
    private MajorCode qnaMajorCode;

    @Enumerated(EnumType.STRING)
    private MemberCode qnaMemberCode; // 단과대만 쓸 예정

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    public PostEntity(Long postId) {
    }

    public static PostEntity from(Long id){
        return new PostEntity(id, null, null, null, null, null, null, null,null, null, null, null,null);
    }

    public static PostEntity of(Long id, String title, String content, Integer viewCount, String thumbnailImage, Status status,
                                /*OngoingStatus ongoingStatus,*/ LocalDateTime lastEditedAt, Category category, SuggestionTarget suggestionTarget, MajorCode qnaMajorCode, MemberCode qnaMemberCode, UserEntity user, BoardEntity board) {
        return new PostEntity(id, title, content, viewCount, thumbnailImage, status, /*ongoingStatus,*/ lastEditedAt, category, suggestionTarget, qnaMajorCode, qnaMemberCode, user, board);
    }

    public static void increaseViewCount(PostEntity post) {
        post.viewCount = post.getViewCount() == null ? 1 : post.viewCount + 1;
    }

    public static void updateLastEditedAt(PostEntity post) {
        post.lastEditedAt = LocalDateTime.now();
    }

    public void updateCategory(Category newCategory) {
        this.category = newCategory;
//        this.ongoingStatus = newStatus;
    }
    public void updateStatus(Status newStatus) {
        this.status = newStatus;
    }
}
