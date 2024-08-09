package ussum.homepage.infra.jpa.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ussum.homepage.domain.post.Category;
import ussum.homepage.domain.post.CategoryRepository;
import ussum.homepage.infra.jpa.post.entity.CategoryCode;
import ussum.homepage.infra.jpa.post.repository.CategoryJpaRepository;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {
    private final CategoryJpaRepository categoryJpaRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Optional<Category> findByCategoryCode(String categoryCode) {
        return categoryJpaRepository.findByCategoryCode(CategoryCode.getEnumCategoryCodeFromStringCategoryCode(categoryCode))
                .map(categoryMapper::toDomain);
    }

    @Override
    public Optional<Category> findById(Long categoryId) {
        return categoryJpaRepository.findById(categoryId).map(categoryMapper::toDomain);
    }
}
