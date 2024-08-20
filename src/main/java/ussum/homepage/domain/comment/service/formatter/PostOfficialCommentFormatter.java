package ussum.homepage.domain.comment.service.formatter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.application.comment.service.dto.response.PostOfficialCommentResponse;
import ussum.homepage.domain.comment.PostComment;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.service.UserReader;


@Service
@RequiredArgsConstructor
public class PostOfficialCommentFormatter implements ussum.homepage.domain.comment.service.PostOfficialCommentFormatter {
    private final UserReader userReader;

    @Override
    public PostOfficialCommentResponse format(PostComment postComment, Long userId) {
        User user = userReader.getUserWithId(postComment.getUserId());
        Boolean isAuthor = userId != null && userId.equals(postComment.getUserId());

        return PostOfficialCommentResponse.of(postComment, user.getName(), postComment.getCommentType(), isAuthor);
    }
}
