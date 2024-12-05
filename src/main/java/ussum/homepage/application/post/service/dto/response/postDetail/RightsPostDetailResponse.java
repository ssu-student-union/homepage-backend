package ussum.homepage.application.post.service.dto.response.postDetail;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import ussum.homepage.application.comment.service.dto.response.PostOfficialCommentResponse;
import ussum.homepage.application.post.service.dto.response.FileResponse;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.RightsDetail;
import ussum.homepage.domain.user.User;
import ussum.homepage.infra.jpa.post.entity.RightsDetailEntity;

@Getter
public class RightsPostDetailResponse extends PostDetailResDto{
    private List<FileResponse> postFileList;
    private List<PostOfficialCommentResponse> officialCommentList;
    private List<RightsDetail> rightsDetailList;

    @Builder
    private RightsPostDetailResponse(Long postId, String categoryName, String authorName, String title, String content, String createdAt, String lastEditedAt, Boolean isAuthor,
                                     List<FileResponse> postFileList, List<PostOfficialCommentResponse> officialCommentList,
                                     List<String> canAuthority, List<RightsDetail> rightsDetailList) {
        super(postId,categoryName,authorName,title,content,createdAt,lastEditedAt,isAuthor,canAuthority);
        this.postFileList = postFileList;
        this.officialCommentList = officialCommentList;
        this.rightsDetailList = rightsDetailList;
    }

    public static RightsPostDetailResponse of(Post post, Boolean isAuthor, User user, String category, List<FileResponse> postFileList,
                                              List<PostOfficialCommentResponse> officialCommentList, List<RightsDetail> rightsDetailList) {
     return RightsPostDetailResponse.builder()
             .postId(post.getId())
             .categoryName(category)
             .authorName(user.getName())
             .title(post.getTitle())
             .content(post.getContent())
             .createdAt(post.getCreatedAt())
             .lastEditedAt(post.getLastEditedAt())
             .postFileList(postFileList)
             .officialCommentList(officialCommentList)
             .rightsDetailList(rightsDetailList)
             .isAuthor(isAuthor)
             .build();
    }
}
