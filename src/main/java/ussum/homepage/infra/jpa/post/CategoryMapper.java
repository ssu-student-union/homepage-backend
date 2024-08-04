package ussum.homepage.infra.jpa.post;

import org.springframework.stereotype.Component;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.Category;
import ussum.homepage.infra.jpa.post.entity.BoardCode;
import ussum.homepage.infra.jpa.post.entity.BoardEntity;
import ussum.homepage.infra.jpa.post.entity.CategoryCode;
import ussum.homepage.infra.jpa.post.entity.CategoryEntity;
import ussum.homepage.infra.jpa.user.entity.MajorCode;

@Component
public class CategoryMapper {
    public Category toDomain(CategoryEntity categoryEntity){
        return Category.of(
                categoryEntity.getId(),
                String.valueOf(categoryEntity.getCategoryCode()),
                categoryEntity.getName(),
                categoryEntity.getBoardEntity().getId(),
                categoryEntity.getCreatedAt(),
                categoryEntity.getUpdatedAt()
        );
    }

    public CategoryEntity toEntity(Category category) {
        CategoryEntity from = CategoryEntity.from(category.getId());
        return CategoryEntity.of(
                category.getId(),
                CategoryCode.getEnumCategoryCodeFromStringCategoryCode(category.getCategoryCode()),
                category.getName(),
                from.getBoardEntity()
        );
    }
}
