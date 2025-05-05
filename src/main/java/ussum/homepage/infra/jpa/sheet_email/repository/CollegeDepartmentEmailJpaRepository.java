//package ussum.homepage.infra.jpa.sheet_email.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import ussum.homepage.infra.jpa.sheet_email.entity.CollegeDepartmentEmailEntity;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface CollegeDepartmentEmailJpaRepository extends JpaRepository<CollegeDepartmentEmailEntity, Long> {
//    Optional<CollegeDepartmentEmailEntity>  findByName(String Name);
//    void deleteAll();
//    void saveAll(List<CollegeDepartmentEmailEntity> list);
//}
