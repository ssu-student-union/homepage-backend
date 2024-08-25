package ussum.homepage.infra.utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ussum.homepage.application.post.service.dto.request.PostFileDeleteRequest;
import ussum.homepage.global.error.exception.GeneralException;
import ussum.homepage.global.error.status.ErrorStatus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class S3utils {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /*
    bucket내의 파일은 하나만 존재.
     */
    public String uploadFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();

        // 기존 파일 삭제, 계속 같은 파일 이름으로 업로드한다고 가정.
        if (amazonS3.doesObjectExist(bucket, originalFilename)) {
            amazonS3.deleteObject(bucket, originalFilename);
        }

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try {
            amazonS3.putObject(bucket, originalFilename, file.getInputStream(), metadata);
        } catch (IOException e) {
            throw new GeneralException(ErrorStatus.S3_ERROR);
        }
        return amazonS3.getUrl(bucket, originalFilename).toString();
    }

    public List<Map<String, String>> uploadFileWithPath(Long userId, String boardCode, MultipartFile[] files, MultipartFile[] images) {
        List<Map<String, String>> uploadedFileUrls = new ArrayList<>();

        if (images != null && images.length > 0) {
            uploadedFileUrls.addAll(uploadFiles(userId, boardCode, images, "images"));
        }

        if (files != null && files.length > 0) {
            uploadedFileUrls.addAll(uploadFiles(userId, boardCode, files, "files"));
        }

        return uploadedFileUrls;
    }

    private List<Map<String, String>> uploadFiles(Long userId, String boardCode, MultipartFile[] files, String fileType) {
        List<Map<String, String>> uploadedFileUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            String fileExtension = fileName.substring(fileName.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

            String folderPath = boardCode + "/" + userId + "/" + fileType + "/";
            String fileKey = folderPath + uniqueFileName;

            try {
                File convertedFile = convertMultiPartToFile(file);
                amazonS3.putObject(new PutObjectRequest(bucket, fileKey, convertedFile));
                convertedFile.delete(); // 임시 파일 삭제

                String fileUrl = amazonS3.getUrl(bucket, fileKey).toString();
                // Map에 fileType과 fileUrl을 저장
                Map<String, String> fileData = new HashMap<>();
                fileData.put(fileType, fileUrl);
                uploadedFileUrls.add(fileData);
            } catch (IOException e) {
                throw new GeneralException(ErrorStatus.S3_ERROR);
            }
        }
        return uploadedFileUrls;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    public int deleteFiles(PostFileDeleteRequest request) {
        try {
            List<DeleteObjectsRequest.KeyVersion> keys = request.fileUrls().stream()
                    .map(this::extractKeyFromUrl)
                    .map(DeleteObjectsRequest.KeyVersion::new)
                    .collect(Collectors.toList());

            DeleteObjectsRequest deleteRequest = new DeleteObjectsRequest(bucket)
                    .withKeys(keys)
                    .withQuiet(false);

            DeleteObjectsResult result = amazonS3.deleteObjects(deleteRequest);

            int deletedCount = result.getDeletedObjects().size();
            return deletedCount;
        } catch (Exception e) {
            throw new GeneralException(ErrorStatus.URL_DELETE_ERROR);
        }
    }

    private String extractKeyFromUrl(String fileUrl) {
        try {
            URL url = new URL(fileUrl);
            return url.getPath().substring(1);
        } catch (Exception e) {
            throw new GeneralException(ErrorStatus.INVALID_S3_URL);
        }
    }

    public void getFileToProject(String fileName)  {
        S3Object s3Object = amazonS3.getObject(bucket, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            saveFile(inputStream, "**/src/main/resources/csv");
        } catch (IOException e) {
            throw new GeneralException(ErrorStatus.S3_ERROR);
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


