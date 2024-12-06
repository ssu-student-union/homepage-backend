package ussum.homepage.domain.csv_user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.csv_user.StudentCsvRepository;

@Service
@RequiredArgsConstructor
public class StudentCsvModifier {
    private final StudentCsvRepository studentCsvRepository;

}
