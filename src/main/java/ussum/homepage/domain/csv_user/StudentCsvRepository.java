package ussum.homepage.domain.csv_user;

import org.springframework.batch.item.Chunk;
import ussum.homepage.infra.jpa.csv_user.entity.StudentCsvEntity;

import java.util.Optional;

public interface StudentCsvRepository {
    void saveAll(Chunk<StudentCsvEntity> students);
    Optional<StudentCsv> findByStudentId(Long studentId);
}
