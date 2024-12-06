package ussum.homepage.application.reaction.service.dto.response;


public record PostCommentReactionCountResponse(
        Integer likeCount
) {
    public static PostCommentReactionCountResponse of(Integer likeCount) {
        return new PostCommentReactionCountResponse(likeCount);
    }
}
