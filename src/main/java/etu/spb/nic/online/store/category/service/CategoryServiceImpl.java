package etu.spb.nic.online.store.category.service;

import etu.spb.nic.online.store.category.dto.CategoryDto;
import etu.spb.nic.online.store.category.mapper.CategoryMapper;
import etu.spb.nic.online.store.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        return CategoryMapper.categoryToCategoryDto(categoryRepository
                .save(CategoryMapper.categoryDtoToCategory(categoryDto)));
    }
}
