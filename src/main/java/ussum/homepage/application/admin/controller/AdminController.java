package ussum.homepage.application.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ussum.homepage.application.admin.service.AdminService;
import ussum.homepage.global.ApiResponse;

@RequiredArgsConstructor
@RequestMapping("/admin")
@RestController
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/s3-csv")
    public ResponseEntity<ApiResponse<?>> csvToS3(@RequestParam MultipartFile file) {
        adminService.uploadCsvToS3(file);
        return ApiResponse.success(null);
    }

    @PostMapping("csv-project")
    public ResponseEntity<ApiResponse<?>> s3ToProject(@RequestParam("file") String fileName) {
        adminService.uploadCsvFromS3ToProject(fileName);
        return ApiResponse.success(null);
    }
}
