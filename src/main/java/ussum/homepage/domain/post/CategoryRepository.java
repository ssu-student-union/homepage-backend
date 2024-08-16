package ussum.homepage.domain.post;

import java.util.Optional;

public interface CategoryRepository {
    Optional<Category> findByCategoryCode(String categoryCode);

    Optional<Category> findById(Long categoryId);
}
