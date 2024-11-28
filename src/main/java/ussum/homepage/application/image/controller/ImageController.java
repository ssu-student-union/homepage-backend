package ussum.homepage.application.image.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ussum.homepage.application.image.controller.dto.request.FileUploadConfirmRequest;
import ussum.homepage.application.image.controller.dto.request.FileUploadRequest;
import ussum.homepage.application.image.controller.dto.response.PreSignedUrlResponse;
import ussum.homepage.application.image.service.ImageService;
import ussum.homepage.application.post.service.dto.response.postSave.PostFileListResponse;
import ussum.homepage.global.ApiResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/presigned-url")
    public ResponseEntity<ApiResponse<?>> getPreSignedUrls(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "boardCode") String boardCode,
            @RequestBody FileUploadRequest request
    ) {
        return ApiResponse.success(
                imageService.createPreSignedUrls(
                        userId,
                        boardCode,
                        request.files(),
                        request.images()
                )
        );
    }

    @PostMapping("/confirm")
    public ResponseEntity<ApiResponse<?>> confirmUpload(
            @RequestParam(value = "userId") Long userId,
            @RequestBody List<FileUploadConfirmRequest> confirmRequests
    ) {
        return ApiResponse.success(
                imageService.confirmUpload(userId, confirmRequests)
        );
    }
}
