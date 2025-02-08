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

    @Builder
    protected QnAPostResponse(Long postId, String title, String content, String date, String category, String department, String college) {
        super(postId, title, content, date, category);
        this.department = department;
        this.college = college;
    }

    public static QnAPostResponse of(Post post, Member member) {
        return QnAPostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .date(post.getCreatedAt())
                .category(post.getCategory())
                .department(MajorCode.getEnumMajorCodeFromStringMajorCode(member.getMajorCode()).getStringMajorCode())
                .college(MemberCode.getEnumMemberCodeFromStringMemberCode(member.getMemberCode()).getStringMemberCode())
                .build();
    }
}
