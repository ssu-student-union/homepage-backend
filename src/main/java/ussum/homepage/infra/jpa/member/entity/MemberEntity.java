package ussum.homepage.infra.jpa.member.entity;
import jakarta.persistence.*;
import lombok.*;
import ussum.homepage.infra.jpa.BaseEntity;
import ussum.homepage.infra.jpa.group.entity.GroupEntity;
import ussum.homepage.infra.jpa.user.entity.UserEntity;


@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isAdmin;

    @Enumerated(EnumType.STRING)
    private MemberCode memberCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groups_id")
    private GroupEntity groupEntity;

    public static MemberEntity of(Long id, Boolean isAdmin, MemberCode memberCode, UserEntity userEntity, GroupEntity groupEntity) {
        return new MemberEntity(id, isAdmin, memberCode, userEntity, groupEntity);
    }

    public static MemberEntity from(Long id) {
        return new MemberEntity(id, null, null, null, null);
    }
}
