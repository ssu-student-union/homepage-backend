package ussum.homepage.domain.comment.service;

import ussum.homepage.application.comment.service.dto.response.PostCommentResponse;
import ussum.homepage.domain.comment.PostComment;


public interface PostCommentFormatter {
    PostCommentResponse format(Long postId, Long userId, String commentType);
    PostCommentResponse format(PostComment postComment);

}
