package ussum.homepage.application.post.service.dto.response.postList;

import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.user.User;
import ussum.homepage.infra.jpa.member.entity.MajorCode;
import ussum.homepage.infra.jpa.member.entity.MemberCode;

@Getter
public class QnAPostResponse extends PostListResDto {

    String department;
    String college;
    String authorName; // 마스킹된 이름을 응답함 단, 자치기구 계정은 제외 ex) 장*호, 총학생회

    @Builder
    protected QnAPostResponse(Long postId, String title, String content, String date, String category, String department, String college, String authorName) {
        super(postId, title, content, date, category);
        this.department = department;
        this.college = college;
        this.authorName = authorName;
    }

    public static QnAPostResponse of(Post post, Member member, User user) {

        return QnAPostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .date(post.getCreatedAt())
                .category(post.getCategory())
                .department(MajorCode.getEnumMajorCodeFromStringMajorCode(member.getMajorCode()).getStringMajorCode())
                .college(MemberCode.getEnumMemberCodeFromStringMemberCode(member.getMemberCode()).getStringMemberCode())
                .authorName(member.getIsAdmin() ? user.getName() : User.maskedName(user.getName()))
                .build();
    }
}
