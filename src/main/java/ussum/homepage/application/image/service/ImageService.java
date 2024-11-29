package ussum.homepage.application.image.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.application.image.controller.dto.request.FileRequest;
import ussum.homepage.application.image.controller.dto.request.FileUploadConfirmRequest;
import ussum.homepage.application.image.controller.dto.response.PreSignedUrlResponse;
import ussum.homepage.application.post.service.dto.response.postSave.PostFileListResponse;
import ussum.homepage.application.post.service.dto.response.postSave.PostFileResponse;
import ussum.homepage.domain.post.PostFile;
import ussum.homepage.domain.post.service.PostFileAppender;
import ussum.homepage.infra.utils.S3PreSignedUrlUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {
    private final S3PreSignedUrlUtils s3PreSignedUrlUtils;
    private final PostFileAppender postFileAppender;

    public PreSignedUrlResponse createPreSignedUrls(
            Long userId,
            String boardCode,
            List<FileRequest> files,
            List<FileRequest> images
    ) {
        List<Map<String, String>> preSignedUrls = new ArrayList<>();
        List<String> originalFileNames = new ArrayList<>();

        // 이미지 파일 처리
        if (!CollectionUtils.isEmpty(images)) {
            List<Map<String, String>> imageUrls = s3PreSignedUrlUtils.generatePreSignedUrlWithPath(
                    userId,
                    boardCode,
                    images.stream().map(FileRequest::fileName).toList(),
                    images.stream().map(FileRequest::contentType).toList(),
                    "images"
            );
            preSignedUrls.addAll(imageUrls);
            originalFileNames.addAll(images.stream().map(FileRequest::fileName).toList());
        }

        // 일반 파일 처리
        if (!CollectionUtils.isEmpty(files)) {
            List<Map<String, String>> fileUrls = s3PreSignedUrlUtils.generatePreSignedUrlWithPath(
                    userId,
                    boardCode,
                    files.stream().map(FileRequest::fileName).toList(),
                    files.stream().map(FileRequest::contentType).toList(),
                    "files"
            );
            preSignedUrls.addAll(fileUrls);
            originalFileNames.addAll(files.stream().map(FileRequest::fileName).toList());
        }

        return PreSignedUrlResponse.of(preSignedUrls, originalFileNames);
    }

    @Transactional
    public PostFileListResponse confirmUpload(Long userId, List<FileUploadConfirmRequest> confirmRequests) {
        List<PostFile> postFiles = confirmRequests.stream()
                .map(request -> PostFile.builder()
                        .url(request.fileUrl())
                        .typeName(request.fileType())
                        .build())
                .toList();

        List<PostFile> afterSaveList = postFileAppender.saveAllPostFile(postFiles);

        String thumbnailUrl = afterSaveList.stream()
                .filter(postFile -> postFile.getTypeName().equals("images"))
                .min(Comparator.comparing(PostFile::getId))
                .map(PostFile::getUrl)
                .orElse(null);

        List<PostFileResponse> postFileResponses = IntStream.range(0, afterSaveList.size())
                .mapToObj(i -> PostFileResponse.of(
                        afterSaveList.get(i).getId(),
                        afterSaveList.get(i).getUrl(),
                        confirmRequests.get(i).originalFileName()
                ))
                .toList();

        return PostFileListResponse.of(thumbnailUrl, postFileResponses);
    }
}
