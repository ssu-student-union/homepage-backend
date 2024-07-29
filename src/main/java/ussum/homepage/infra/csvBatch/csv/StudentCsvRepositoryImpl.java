package ussum.homepage.infra.csvBatch.csv;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Repository;
import ussum.homepage.infra.csvBatch.csv.entity.StudentCsvData;
import ussum.homepage.infra.csvBatch.csv.repository.StudentCsvJpaRepository;
import ussum.homepage.infra.csvBatch.csv.repository.StudentCsvRepository;

@Repository
@RequiredArgsConstructor
public class StudentCsvRepositoryImpl implements StudentCsvRepository {
    private final StudentCsvJpaRepository studentCsvJpaRepository;

    @Override
    public void saveAll(Chunk<StudentCsvData> students) {
        studentCsvJpaRepository.saveAll(students);
    }
}
