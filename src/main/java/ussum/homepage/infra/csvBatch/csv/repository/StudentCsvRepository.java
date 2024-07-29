package ussum.homepage.infra.csvBatch.csv.repository;

import org.springframework.batch.item.Chunk;
import ussum.homepage.infra.csvBatch.csv.entity.StudentCsvData;

public interface StudentCsvRepository {
    void saveAll(Chunk<StudentCsvData> students);
}
