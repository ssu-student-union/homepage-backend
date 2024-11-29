package ussum.homepage.infra.utils;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.util.*;

@RequiredArgsConstructor
@Component
public class S3PreSignedUrlUtils {
    private final AmazonS3 amazonS3;
    private final ContentTypeValidator contentTypeValidator;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.presigned-url.expiration}")
    private int expirationSeconds;

    public Map<String, String> generatePreSignedUrl(String fileName, String contentType) {

        contentTypeValidator.validate(contentType);

        // URL 만료 시간 설정
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000L * expirationSeconds; // 초 단위를 밀리초로 변환
        expiration.setTime(expTimeMillis);

        // PreSigned URL 생성을 위한 요청 객체 생성
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, fileName)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(expiration);

        // content-type 설정
        generatePresignedUrlRequest.addRequestParameter(
                "Content-Type", contentType
        );

        // PreSigned URL 생성
        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

        Map<String, String> result = new HashMap<>();
        result.put("preSignedUrl", url.toString());
        result.put("fileUrl", String.format("https://%s.s3.amazonaws.com/%s", bucket, fileName));

        return result;
    }

    public List<Map<String, String>> generatePreSignedUrlWithPath(
            Long userId,
            String boardCode,
            List<String> fileNames,
            List<String> contentTypes,
            String fileType
    ) {
        int totalFiles = fileNames.size();

        // 입력값 검증 추가
        if (totalFiles != contentTypes.size()) {
            throw new IllegalArgumentException("파일명 목록과 컨텐트 타입 목록의 크기가 일치하지 않습니다.");
        }

        List<Map<String, String>> urls = new ArrayList<>();

        for (int i = 0; i < totalFiles; i++) {
            String originalFileName = fileNames.get(i);
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

            String folderPath = boardCode + "/" + userId + "/" + fileType + "/";
            String fileKey = folderPath + uniqueFileName;

            Map<String, String> urlInfo = generatePreSignedUrl(fileKey, contentTypes.get(i));
            urls.add(urlInfo);
        }

        return urls;
    }
}
