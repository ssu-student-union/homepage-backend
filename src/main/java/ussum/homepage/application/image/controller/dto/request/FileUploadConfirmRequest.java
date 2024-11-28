package ussum.homepage.application.image.controller.dto.request;

public record FileUploadConfirmRequest(
        String fileUrl,
        String fileType,
        String originalFileName
) {
}
