package ussum.homepage.infra.jpa.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ussum.homepage.infra.jpa.post.entity.CategoryCode;
import ussum.homepage.infra.jpa.post.entity.CategoryEntity;

import java.util.Optional;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByCategoryCode(CategoryCode categoryCode);
}
