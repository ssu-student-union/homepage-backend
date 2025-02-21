package ussum.homepage.infra.utils;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ussum.homepage.application.image.service.dto.PreSignedUrlInfo;
import ussum.homepage.infra.utils.s3.S3FileException;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

import static ussum.homepage.global.error.status.ErrorStatus.S3_VALIDATION_ERROR;

@RequiredArgsConstructor
@Component
public class S3PreSignedUrlUtils {
    private final AmazonS3 amazonS3;
    private final ContentTypeValidator contentTypeValidator;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.presigned-url.expiration}")
    private int expirationSeconds;

    public PreSignedUrlInfo generatePreSignedUrl(String fileName, String contentType, String originalFileName) {
        contentTypeValidator.validate(contentType);

        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * expirationSeconds;
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, fileName)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(expiration);

        generatePresignedUrlRequest.addRequestParameter(
                "Content-Type", contentType
        );

        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        String fileUrl = String.format("https://%s.s3.amazonaws.com/%s", bucket, fileName);

        return PreSignedUrlInfo.of(
                url.toString(),
                fileUrl,
                originalFileName,
                LocalDateTime.ofInstant(expiration.toInstant(), ZoneId.systemDefault())
        );
    }

    public List<PreSignedUrlInfo> generatePreSignedUrlWithPath(
            Long userId,
            String boardCode,
            List<String> fileNames,
            List<String> contentTypes,
            String fileType
    ) {
        int totalFiles = fileNames.size();

        if (totalFiles != contentTypes.size()) {
            throw new IllegalArgumentException("파일명 목록과 컨텐트 타입 목록의 크기가 일치하지 않습니다.");
        }

        return IntStream.range(0, totalFiles)
                .mapToObj(i -> {
                    String originalFileName = fileNames.get(i);
                    String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                    String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
                    String folderPath = boardCode + "/" + userId + "/" + fileType + "/";
                    String fileKey = folderPath + uniqueFileName;

                    return generatePreSignedUrl(fileKey, contentTypes.get(i), originalFileName);
                })
                .toList();
    }

    public CompletableFuture<Boolean> doesObjectExistAsync(String fileUrl) {
        return CompletableFuture.supplyAsync(() -> {
            String fileKey = extractKeyFromUrl(fileUrl);
            return amazonS3.doesObjectExist(bucket, fileKey);
        });
    }

    private String extractKeyFromUrl(String fileUrl) {
        try {
            return fileUrl.substring(fileUrl.indexOf(bucket) + bucket.length() + 1);
        } catch (IndexOutOfBoundsException e) {
            throw new S3FileException.InvalidS3UrlException(S3_VALIDATION_ERROR);
        }
    }
}
