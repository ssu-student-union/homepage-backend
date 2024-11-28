package ussum.homepage.application.image.controller.dto.request;

import java.util.Collections;
import java.util.List;

public record FileUploadRequest(
        List<FileRequest> files,
        List<FileRequest> images
) {
    public static FileUploadRequest of(List<FileRequest> files, List<FileRequest> images) {
        return new FileUploadRequest(
                files != null ? files : Collections.emptyList(),
                images != null ? images : Collections.emptyList()
        );
    }

    // null check를 위한 기본 생성자
    public FileUploadRequest {
        files = files != null ? files : Collections.emptyList();
        images = images != null ? images : Collections.emptyList();
    }
}
