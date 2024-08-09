package ussum.homepage.application.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.application.comment.service.dto.response.CommentListResponse;
import ussum.homepage.application.comment.service.dto.response.PostCommentResponse;
import ussum.homepage.domain.comment.PostComment;
import ussum.homepage.domain.comment.service.*;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostCommentManageService {
    private final PostCommentReader postCommentReader;
    private final PostCommentFormatter postCommentFormatter;

    public CommentListResponse getCommentList(Long postId, String type) {
        List<PostComment> postComments = postCommentReader.getCommentListWithPostIdAndType(postId, type);

        List<PostCommentResponse> postCommentResponses = postComments.stream()
                .map(postCommentFormatter::format)
                .toList();

        return CommentListResponse.of(postCommentResponses, postCommentResponses.size());
    }
}
