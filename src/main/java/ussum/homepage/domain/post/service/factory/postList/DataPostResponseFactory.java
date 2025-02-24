package ussum.homepage.domain.post.service.factory.postList;

import ussum.homepage.application.post.service.dto.response.postList.DataPostResponse;
import ussum.homepage.application.post.service.dto.response.FileResponse;
import ussum.homepage.application.post.service.dto.response.postList.PostListResDto;
import ussum.homepage.domain.member.service.MemberReader;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.PostFile;
import ussum.homepage.domain.post.service.PostReader;
import ussum.homepage.domain.postlike.service.PostReactionReader;
import ussum.homepage.domain.user.service.UserReader;

import java.util.List;

public class DataPostResponseFactory implements PostListResponseFactory {
    @Override
    public PostListResDto createResponse(Post post, PostReader postReader, PostReactionReader postReactionReader, UserReader userReader, MemberReader memberReader) {
        // 이 메서드는 일반적인 경우에 사용되지 않을 것이므로, 예외를 던지거나 null을 반환할 수 있습니다.
        throw new UnsupportedOperationException("Use createDataResponse for DataPostResponse");
    }

    @Override
    public PostListResDto createDataResponse(Post post, List<PostFile> postFiles) {
        List<FileResponse> fileResponses = postFiles.stream()
                .map(FileResponse::of)
                .toList();

        String content = postFiles.stream()
                .findFirst()
                .map(PostFile::getFileCategory)
                .orElse(null); // 파일이 없는 경우 null로 설정

        return DataPostResponse.of(post, fileResponses, content);
    }
}
