package ussum.homepage.domain.comment.service;

import ussum.homepage.application.comment.service.dto.response.PostOfficialCommentResponse;
import ussum.homepage.domain.comment.PostComment;

public interface PostOfficialCommentFormatter {
    PostOfficialCommentResponse format(PostComment postComment, Long userId);
}
