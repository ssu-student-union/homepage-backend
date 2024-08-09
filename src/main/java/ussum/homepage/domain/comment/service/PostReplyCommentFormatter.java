package ussum.homepage.domain.comment.service;


import ussum.homepage.application.comment.service.dto.response.PostReplyCommentResponse;

public interface PostReplyCommentFormatter {
    PostReplyCommentResponse format(Long commentId, Long userId);
}
