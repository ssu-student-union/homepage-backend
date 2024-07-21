package ussum.homepage.application.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.service.UserReader;
import ussum.homepage.infra.utils.CsvUtils;

import java.lang.reflect.Field;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final CsvUtils csvUtils;
    private final UserReader userReader;


    public void updateStudentStatus(MultipartFile file) {
        csvUtils.updateStudentStatusFromCsv(file);
        return;
    }
}
