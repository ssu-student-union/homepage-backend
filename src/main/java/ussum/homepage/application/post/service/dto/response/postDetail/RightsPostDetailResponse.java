package ussum.homepage.application.post.service.dto.response.postDetail;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import ussum.homepage.application.comment.service.dto.response.PostOfficialCommentResponse;
import ussum.homepage.application.post.service.dto.response.FileResponse;

@Getter
public class RightsPostDetailResponse extends PostDetailResDto{
    private List<FileResponse> fileResponseList;
    private List<PostOfficialCommentResponse> postOfficialCommentResponseList;

    @Builder
    private RightsPostDetailResponse(Long postId, String categoryName, String authorName, String studentId, String title, String content, String createdAt, String lastEditedAt, Boolean isAuthor,
                                     List<FileResponse> fileResponseList, List<PostOfficialCommentResponse> officialCommentList,
                                     List<String> canAuthority ){
        super(postId,categoryName,authorName,title,content,createdAt,lastEditedAt,isAuthor,canAuthority);
        this.fileResponseList = fileResponseList;
        this.postOfficialCommentResponseList = officialCommentList;
    }
}
