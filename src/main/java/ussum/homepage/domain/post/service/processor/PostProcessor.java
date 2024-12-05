package ussum.homepage.domain.post.service.processor;

import ussum.homepage.domain.post.Post;

public interface PostProcessor {

    public void onAdminCommentPosted(Long postId, String commentType);

    public void handleReceivedStatus(Post post);

    public Boolean isAnsweredByAdmin(Post post);

    public void updatePostCategoryAndOngoingStatus(Post post, String category);

    public String getBoardType();
}
