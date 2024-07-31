package ussum.homepage.application.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ussum.homepage.domain.user.service.UserReader;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserReader userReader;


    public void updateStudentStatus(MultipartFile file) {

    }
}
