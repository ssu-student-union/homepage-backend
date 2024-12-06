package ussum.homepage.application.comment.service.dto.response;

import org.springframework.data.domain.Page;
import ussum.homepage.domain.comment.PostComment;
import ussum.homepage.domain.comment.service.formatter.TriFunction;

import java.util.List;

public record PostCommentListResponse(
        List<PostCommentResponse> postComments,
        Integer total
) {
    public static PostCommentListResponse of(Page<PostComment> postComments, Long total, TriFunction<Long, Long, String, PostCommentResponse> formatter){
        return new PostCommentListResponse(
                postComments.map(postComment -> formatter.apply(postComment.getPostId(), postComment.getUserId(), postComment.getCommentType())).toList(),
                Math.toIntExact(total)
        );
    }
}
