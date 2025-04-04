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

    @Enumerated(EnumType.STRING)
    private MajorCode majorCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY) // 일단  cascade = CascadeType.PERSIST 추가, 영속화 문제 해결
    @JoinColumn(name = "groups_id")
    private GroupEntity groupEntity;

    private Boolean isVerified;

    public static MemberEntity of(Long id, Boolean isAdmin, MemberCode memberCode, MajorCode majorCode, UserEntity userEntity, GroupEntity groupEntity, Boolean isVerified) {
        return new MemberEntity(id, isAdmin, memberCode, majorCode, userEntity, groupEntity, isVerified);
    }
    public static MemberEntity nullGroup(Long id, Boolean isAdmin, MemberCode memberCode, MajorCode majorCode, UserEntity userEntity, Boolean isVerified) {
        return new MemberEntity(id, isAdmin, memberCode, majorCode, userEntity, null, isVerified);
    }

    public static MemberEntity from(Long id) {
        return new MemberEntity(id, null, null, null, null, null, null);
    }
}
