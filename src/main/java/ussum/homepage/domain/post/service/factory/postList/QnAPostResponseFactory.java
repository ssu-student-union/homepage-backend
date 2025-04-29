package ussum.homepage.domain.post.service.factory.postList;

import ussum.homepage.application.post.service.dto.response.postList.PostListResDto;
import ussum.homepage.application.post.service.dto.response.postList.QnAPostResponse;
import ussum.homepage.application.post.service.dto.response.postList.ServiceNoticePostResponse;
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.member.exception.MemberNotFoundException;
import ussum.homepage.domain.member.service.MemberReader;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.service.PostReader;
import ussum.homepage.domain.postlike.service.PostReactionReader;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.service.UserReader;

import java.util.List;
import java.util.Optional;

import static ussum.homepage.global.error.status.ErrorStatus.MEMBER_NOT_FOUND;

public class QnAPostResponseFactory implements PostListResponseFactory{

    @Override
    public PostListResDto createResponse(Post post, PostReader postReader, PostReactionReader postReactionReader, UserReader userReader, MemberReader memberReader) {
        // TODO(inho): 일반 user는 member 데이터가 중복되지 않아 괜찮지만, 중복될 수 있는 자치기구라던지 단과대 아이디 등은 점검해볼 필요가 있습니다.
        List<Member> members = memberReader.getMembersWithUserId(post.getUserId());
        User user = userReader.getUserWithId(post.getUserId());
        Optional<Member> firstMember = members.stream().findFirst();
        if (firstMember.isEmpty()) {
            throw new MemberNotFoundException(MEMBER_NOT_FOUND);
        }
        return QnAPostResponse.of(post, firstMember.get(), user);
    }
}
