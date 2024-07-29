package ussum.homepage.infra.csvBatch.csv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ussum.homepage.infra.csvBatch.csv.entity.StudentCsvData;

public interface StudentCsvJpaRepository extends JpaRepository<StudentCsvData, Long> {
}
