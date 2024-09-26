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
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.service.PostReader;
import ussum.homepage.domain.reaction.service.PostCommentReactionManager;
import ussum.homepage.domain.reaction.service.PostReplyCommentReactionManager;


import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostCommentManageService {
    private final PostCommentReader postCommentReader;
    private final PostCommentFormatter postCommentFormatter;

    public CommentListResponse getCommentList(Long userId, Long postId, String type) {
        List<PostComment> postComments = postCommentReader.getCommentListWithPostIdAndType(postId, type);

        List<PostCommentResponse> postCommentResponses = postComments.stream()
                .map(postComment -> postCommentFormatter.format(postComment, userId))
                .toList();

        // 전체 댓글 수 + 각 댓글의 대댓글 수
        Integer totalCommentCount = postCommentResponses.size() + postCommentResponses.stream()
                .mapToInt(postCommentResponse -> postCommentResponse.getPostReplyComments().size())
                .sum();

        return CommentListResponse.of(postCommentResponses, totalCommentCount);
    }

}
