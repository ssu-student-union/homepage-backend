package ussum.homepage.infra.jpa.csv_user;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Repository;
import ussum.homepage.domain.csv_user.StudentCsv;
import ussum.homepage.infra.jpa.csv_user.entity.StudentCsvEntity;
import ussum.homepage.infra.jpa.csv_user.repository.StudentCsvJpaRepository;
import ussum.homepage.domain.csv_user.StudentCsvRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StudentCsvRepositoryImpl implements StudentCsvRepository {
    private final StudentCsvJpaRepository studentCsvJpaRepository;
    private final StudentCsvMapper studentCsvMapper;

    @Override
    public void saveAll(Chunk<StudentCsvEntity> students) {
        studentCsvJpaRepository.saveAll(students);
    }

    @Override
    public Optional<StudentCsv> findByStudentId(Long studentId) {
        return studentCsvJpaRepository.findByStudentId(studentId).map(studentCsvMapper::toDomain);
    }
}
