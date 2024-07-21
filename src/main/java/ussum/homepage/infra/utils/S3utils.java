package ussum.homepage.infra.utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@Slf4j
@Component
public class S3utils {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /*
    bucket내의 파일은 하나만 존재.
     */
    public String uploadFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();

        // 기존 파일 삭제, 계속 같은 파일 이름으로 업로드한다고 가정.
        if (amazonS3.doesObjectExist(bucket, originalFilename)) {
            amazonS3.deleteObject(bucket, originalFilename);
        }

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        amazonS3.putObject(bucket, originalFilename, file.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, originalFilename).toString();
    }

    public S3ObjectInputStream getFile(String fileName)  { // 삭제 필요
        S3Object s3Object = amazonS3.getObject(bucket, fileName);
        return s3Object.getObjectContent();
    }

    public void getFileToProject(String fileName)  {
        S3Object s3Object = amazonS3.getObject(bucket, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            saveFile(inputStream, "**/src/main/resources/csv");
        } catch (IOException e) {
            throw new RuntimeException("파일을 저장하지 못했습니다.");
        }
        return;
    }

    private void saveFile(InputStream inputStream, String filePath) throws IOException {
        File targetFile = new File(filePath);
        try (FileOutputStream outputStream = new FileOutputStream(targetFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }
}
