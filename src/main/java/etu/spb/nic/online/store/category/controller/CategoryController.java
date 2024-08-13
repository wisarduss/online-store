package etu.spb.nic.online.store.category.controller;

import etu.spb.nic.online.store.category.dto.CategoryDto;
import etu.spb.nic.online.store.category.service.CategoryService;
import etu.spb.nic.online.store.item.dto.ItemResponseDto;
import etu.spb.nic.online.store.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalog")
public class CategoryController {

    private final ItemService itemService;
    private final CategoryService categoryService;

    @GetMapping("/{catId}")
    public List<ItemResponseDto> getItemForCatId(@PathVariable Long catId) {
        return itemService.getItemForCatId(catId);
    }

    @PostMapping
    public CategoryDto addCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.addCategory(categoryDto);
    }

    @GetMapping()
    public Map<CategoryDto, List<ItemResponseDto>> getAll() {
        return itemService.getAll();
    }

}
