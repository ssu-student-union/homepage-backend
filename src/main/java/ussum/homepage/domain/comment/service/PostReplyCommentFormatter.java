package ussum.homepage.domain.comment.service;


import ussum.homepage.application.comment.service.dto.response.PostReplyCommentResponse;
import ussum.homepage.domain.comment.PostReplyComment;

public interface PostReplyCommentFormatter {
//    PostReplyCommentResponse format(Long commentId, Long userId);
    PostReplyCommentResponse format(PostReplyComment postReplyComment);
}
