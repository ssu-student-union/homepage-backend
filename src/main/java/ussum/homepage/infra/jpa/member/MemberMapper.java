package ussum.homepage.infra.jpa.member;

import org.springframework.stereotype.Component;
import ussum.homepage.domain.member.Member;
import ussum.homepage.infra.jpa.member.entity.MajorCode;
import ussum.homepage.infra.jpa.member.entity.MemberCode;
import ussum.homepage.infra.jpa.member.entity.MemberEntity;
import ussum.homepage.infra.jpa.group.entity.GroupEntity;
import ussum.homepage.infra.jpa.user.entity.UserEntity;

@Component
public class MemberMapper {
    public Member toDomain(MemberEntity memberEntity) {
        return Member.of(
                memberEntity.getId(),
                memberEntity.getIsAdmin(),
                String.valueOf(memberEntity.getMemberCode()),
                String.valueOf(memberEntity.getMajorCode()),
                memberEntity.getUserEntity().getId(),
                memberEntity.getGroupEntity().getId()
        );
    }

    public MemberEntity toEntity(Member member) {
        return MemberEntity.of(
                member.getId(),
                member.getIsAdmin(),
                MemberCode.getEnumMemberCodeFromStringMemberCode(member.getMemberCode()),
                MajorCode.getEnumMajorCodeFromStringMajorCode(member.getMajorCode()),
                UserEntity.from(member.getUserId()),
                GroupEntity.from(member.getGroupId())
        );
    }
}
