package ussum.homepage.infra.jpa.csv_user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ussum.homepage.infra.jpa.csv_user.entity.StudentCsvEntity;

import java.util.Optional;

public interface StudentCsvJpaRepository extends JpaRepository<StudentCsvEntity, Long> {
    Optional<StudentCsvEntity> findByStudentId(Long id);
}
