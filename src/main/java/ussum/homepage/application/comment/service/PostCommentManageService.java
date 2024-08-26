package ussum.homepage.application.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.application.comment.service.dto.response.CommentListResponse;
import ussum.homepage.application.comment.service.dto.response.PostCommentResponse;
import ussum.homepage.application.post.service.dto.request.PostUserRequest;
import ussum.homepage.domain.comment.PostComment;
import ussum.homepage.domain.comment.service.PostCommentFormatter;
import ussum.homepage.domain.comment.service.PostCommentReader;


import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostCommentManageService {
    private final PostCommentReader postCommentReader;
    private final PostCommentFormatter postCommentFormatter;

    public CommentListResponse getCommentList(Long postId, String type, Long userId) {
        List<PostComment> postComments = postCommentReader.getCommentListWithPostIdAndType(postId, type);

        List<PostCommentResponse> postCommentResponses = postComments.stream()
                .map(postComment -> postCommentFormatter.format(postComment, userId))
                .toList();

        // 전체 댓글 수 + 각 댓글의 대댓글 수
        Integer totalCommentCount = postCommentResponses.size() + postCommentResponses.stream()
                .mapToInt(postCommentResponse -> postCommentResponse.postReplyComments().size())
                .sum();

        return CommentListResponse.of(postCommentResponses, totalCommentCount);
    }

}
