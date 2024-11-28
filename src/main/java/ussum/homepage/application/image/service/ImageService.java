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
        if (images != null && !images.isEmpty()) {
            List<Map<String, String>> imageUrls = s3PreSignedUrlUtils.generatePreSignedUrlWithPath(
                    userId,
                    boardCode,
                    images.stream().map(FileRequest::fileName).collect(Collectors.toList()),
                    images.stream().map(FileRequest::contentType).collect(Collectors.toList()),
                    "images"
            );
            preSignedUrls.addAll(imageUrls);
            originalFileNames.addAll(images.stream().map(FileRequest::fileName).collect(Collectors.toList()));
        }

        // 일반 파일 처리
        if (files != null && !files.isEmpty()) {
            List<Map<String, String>> fileUrls = s3PreSignedUrlUtils.generatePreSignedUrlWithPath(
                    userId,
                    boardCode,
                    files.stream().map(FileRequest::fileName).collect(Collectors.toList()),
                    files.stream().map(FileRequest::contentType).collect(Collectors.toList()),
                    "files"
            );
            preSignedUrls.addAll(fileUrls);
            originalFileNames.addAll(files.stream().map(FileRequest::fileName).collect(Collectors.toList()));
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
                .collect(Collectors.toList());

        List<PostFile> afterSaveList = postFileAppender.saveAllPostFile(postFiles);

        String thumbnailUrl = afterSaveList.stream()
                .filter(postFile -> postFile.getTypeName().equals("images"))
                .min(Comparator.comparing(PostFile::getId))
                .map(PostFile::getUrl)
                .orElse(null);

        AtomicInteger index = new AtomicInteger(0);
        List<PostFileResponse> postFileResponses = afterSaveList.stream()
                .map(postFile -> {
                    int currentIndex = index.getAndIncrement();
                    return PostFileResponse.of(
                            postFile.getId(),
                            postFile.getUrl(),
                            confirmRequests.get(currentIndex).originalFileName()
                    );
                })
                .collect(Collectors.toList());

        return PostFileListResponse.of(thumbnailUrl, postFileResponses);
    }
}
