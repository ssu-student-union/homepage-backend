package ussum.homepage.infra.utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ussum.homepage.application.post.service.dto.request.PostFileDeleteRequest;
import ussum.homepage.application.post.service.dto.response.postSave.PostFileMediatorResponse;
import ussum.homepage.global.error.exception.GeneralException;
import ussum.homepage.global.error.status.ErrorStatus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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
    public PostFileMediatorResponse uploadDataFileWithPath(Long userId, String boardCode, MultipartFile[] files, String fileType) {
        List<Map<String, String>> uploadedFileUrls = new ArrayList<>();
        List<String> originalFileNames = new ArrayList<>();

        if (files != null && files.length > 0) {
            PostFileMediatorResponse imageResponse = uploadFiles(userId, boardCode, files, fileType);
            uploadedFileUrls.addAll(imageResponse.urlList());
            originalFileNames.addAll(formatOriginalFileNames(imageResponse.originalFileNames()));
        }
//
//        if (files != null && files.length > 0) {
//            PostFileMediatorResponse fileResponse = uploadFiles(userId, boardCode, files, "files");
//            uploadedFileUrls.addAll(fileResponse.urlList());
//            originalFileNames.addAll(formatOriginalFileNames(fileResponse.originalFileNames()));
//        }

        return PostFileMediatorResponse.of(originalFileNames, uploadedFileUrls);
    }

    public PostFileMediatorResponse uploadFileWithPath(Long userId, String boardCode, MultipartFile[] files, MultipartFile[] images) {
        List<Map<String, String>> uploadedFileUrls = new ArrayList<>();
        List<String> originalFileNames = new ArrayList<>();

        if (images != null && images.length > 0) {
            PostFileMediatorResponse imageResponse = uploadFiles(userId, boardCode, files, "images");
            uploadedFileUrls.addAll(imageResponse.urlList());
            originalFileNames.addAll(formatOriginalFileNames(imageResponse.originalFileNames()));
        }

        if (files != null && files.length > 0) {
            PostFileMediatorResponse fileResponse = uploadFiles(userId, boardCode, files, "files");
            uploadedFileUrls.addAll(fileResponse.urlList());
            originalFileNames.addAll(formatOriginalFileNames(fileResponse.originalFileNames()));
        }

        return PostFileMediatorResponse.of(originalFileNames, uploadedFileUrls);
    }
    public List<String> formatOriginalFileNames(List<String> originalFileNames) {
        return originalFileNames.stream()
                .map(this::formatSingleFileName)
                .collect(Collectors.toList());
    }

    private String formatSingleFileName(String originalFileName) {
        int lastDotIndex = originalFileName.lastIndexOf('.');

        if (lastDotIndex == -1) {
            // 확장자가 없는 경우 전체 이름을 처리
            return originalFileName.replaceAll("\\s+", " ").trim();
        }

        // 파일 이름과 확장자를 분리
        String nameWithoutExtension = originalFileName.substring(0, lastDotIndex);
        String extension = originalFileName.substring(lastDotIndex + 1).toUpperCase();

        // 파일 이름 부분을 처리하고 확장자를 대문자로 변환하여 끝에 붙임
        String processedName = nameWithoutExtension.replaceAll("\\s+", " ").trim();
        return processedName + " " + extension;
    }

    private PostFileMediatorResponse uploadFiles(Long userId, String boardCode, MultipartFile[] files, String fileType) {
        List<Map<String, String>> uploadedFileUrls = new ArrayList<>();
        List<String> originalFileNames = new ArrayList<>();

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
                originalFileNames.add(fileName); // 원본 파일 이름 추가
            } catch (IOException e) {
                throw new GeneralException(ErrorStatus.S3_ERROR);
            }
        }

        return PostFileMediatorResponse.of(originalFileNames, uploadedFileUrls);
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    public int deleteFiles(PostFileDeleteRequest request) {
        if (request.fileUrls() == null || request.fileUrls().isEmpty()) {
            return 0;
        }

        try {
            List<DeleteObjectsRequest.KeyVersion> keys = request.fileUrls().stream()
                    .map(this::extractKeyFromUrl)
                    .map(DeleteObjectsRequest.KeyVersion::new)
                    .collect(Collectors.toList());
            DeleteObjectsRequest deleteRequest = new DeleteObjectsRequest(bucket)
                    .withKeys(keys);
//                    .withQuiet(false);

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
            String path = url.getPath();
            String key = path.substring(1);
            return URLDecoder.decode(key, StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            throw new GeneralException(ErrorStatus.INVALID_S3_URL);
        }
    }

    public void getFileToProject(String fileName)  {
        S3Object s3Object = amazonS3.getObject(bucket, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        String projectRootPath = System.getProperty("user.dir");
        String filePath = projectRootPath + "/csv/" + fileName;
        try {
            saveFile(inputStream, filePath);
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


