package etu.spb.nic.online.store.category.mapper;

import etu.spb.nic.online.store.category.dto.CategoryDto;
import etu.spb.nic.online.store.category.model.Category;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryMapper {

    public static Category categoryDtoToCategory(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .title(categoryDto.getTitle())
                .build();
    }

    public static CategoryDto categoryToCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .title(category.getTitle())
                .build();
    }
}
