package ussum.homepage.application.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.application.comment.service.dto.PostCommentListResponse;
import ussum.homepage.application.comment.service.dto.PostCommentResponse;
import ussum.homepage.application.comment.service.dto.request.PostCommentCreateRequest;
import ussum.homepage.application.comment.service.dto.request.PostCommentUpdateRequest;
import ussum.homepage.domain.comment.PostComment;
import ussum.homepage.domain.comment.service.PostCommentAppender;
import ussum.homepage.domain.comment.service.PostCommentFormatter;
import ussum.homepage.domain.comment.service.PostCommentModifier;
import ussum.homepage.domain.comment.service.PostCommentReader;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final PostCommentReader postCommentReader;
    private final PostCommentFormatter postCommentFormatter;
    private final PostCommentAppender postCommentAppender;
    private final PostCommentModifier postCommentModifier;
    public PostCommentListResponse getCommentList(/*String boardCode,*/ Long postId, int page, int take, String type){
        Page<PostComment> commentList = postCommentReader.getPostCommentList(setPageable(page, take), postId);
        return PostCommentListResponse.of(commentList, commentList.getTotalElements(), postCommentFormatter::format, type);
    }
    @Transactional
    public PostCommentResponse createComment(Long userId, /*String boardCode,*/ Long postId, PostCommentCreateRequest postCommentCreateRequest){
        PostComment postComment = postCommentAppender.createPostComment(postCommentCreateRequest.toDomain(userId,postId));
        return postCommentFormatter.format(postComment.getPostId(), postComment.getUserId(), null);
    }
    @Transactional
    public PostCommentResponse editComment(Long userId, Long postId, Long commentId, PostCommentUpdateRequest postCommentUpdateRequest){
        PostComment postComment = postCommentModifier.updateComment(userId, postId, commentId, postCommentUpdateRequest);
        return postCommentFormatter.format(postComment.getPostId(), postComment.getUserId(), null);
    }
    @Transactional
    public void deleteComment(Long commentId){
        postCommentModifier.deleteComment(commentId);
    }
    private Pageable setPageable(int page, int take){
        return PageRequest.of(page, take);
    }
}
