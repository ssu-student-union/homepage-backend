package ussum.homepage.application.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.application.comment.service.dto.response.PostCommentListResponse;
import ussum.homepage.application.comment.service.dto.response.PostCommentResponse;
import ussum.homepage.application.comment.service.dto.request.PostCommentCreateRequest;
import ussum.homepage.application.comment.service.dto.request.PostCommentUpdateRequest;
import ussum.homepage.domain.comment.PostComment;
import ussum.homepage.domain.comment.service.PostCommentAppender;
import ussum.homepage.domain.comment.service.PostCommentFormatter;
import ussum.homepage.domain.comment.service.PostCommentModifier;
import ussum.homepage.domain.comment.service.PostCommentReader;
import ussum.homepage.domain.member.service.MemberManager;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final PostCommentReader postCommentReader;
    private final PostCommentFormatter postCommentFormatter;
    private final PostCommentAppender postCommentAppender;
    private final PostCommentModifier postCommentModifier;
    private final MemberManager memberManager;

    public PostCommentListResponse getCommentList(Long postId, int page, int take, String type){
        Page<PostComment> commentList = postCommentReader.getPostCommentList(setPageable(page, take), postId);
        return PostCommentListResponse.of(commentList, commentList.getTotalElements(), postCommentFormatter::format);
    }

    @Transactional
    public PostCommentResponse createComment(Long userId, Long postId, PostCommentCreateRequest postCommentCreateRequest) {
        String commentType = memberManager.getCommentType(userId);
        PostComment postComment = postCommentAppender.createPostComment(postCommentCreateRequest.toDomain(userId, postId, commentType));
        return postCommentFormatter.format(postComment, userId);
    }

    @Transactional
    public PostCommentResponse editComment(Long userId, Long postId, Long commentId, PostCommentUpdateRequest postCommentUpdateRequest){
        PostComment postComment = postCommentReader.getPostComment(commentId);
        PostComment editedPostComment = postCommentModifier.updateComment(postComment, userId, postId, commentId, postCommentUpdateRequest);
//        PostComment postComment = postCommentModifier.updateComment(userId, postId, commentId, , postCommentUpdateRequest);
//        return postCommentFormatter.format(postComment.getPostId(), postComment.getUserId(), postComment.getCommentType());
        return postCommentFormatter.format(editedPostComment, userId);
    }

    @Transactional
    public void deleteComment(Long commentId){
        postCommentModifier.deleteComment(commentId);
    }

    private Pageable setPageable(int page, int take){
        return PageRequest.of(page, take);
    }

}
