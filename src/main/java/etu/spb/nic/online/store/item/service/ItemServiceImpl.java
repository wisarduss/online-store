package etu.spb.nic.online.store.item.service;

import etu.spb.nic.online.store.category.dto.CategoryDto;
import etu.spb.nic.online.store.category.model.Category;
import etu.spb.nic.online.store.category.repository.CategoryRepository;
import etu.spb.nic.online.store.common.exception.IdNotFoundException;
import etu.spb.nic.online.store.common.exception.LassThenZeroException;
import etu.spb.nic.online.store.item.dto.ItemDto;
import etu.spb.nic.online.store.item.dto.ItemResponseDto;
import etu.spb.nic.online.store.item.mapper.ItemMapper;
import etu.spb.nic.online.store.item.model.Item;
import etu.spb.nic.online.store.item.model.ItemStatus;
import etu.spb.nic.online.store.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ItemDto addItem(ItemDto itemDto) {
        Set<Category> categories = new HashSet<>();

        for (Long catId : itemDto.getCatIds()) {
            Category category = categoryRepository.findById(catId)
                    .orElseThrow(() -> new IdNotFoundException(String.format("категория с id = %d не найдена", catId)));
            categories.add(category);
        }

        Item item = ItemMapper.itemDtoToItemWithIds(itemDto, categories);
        return ItemMapper.itemToItemDto(itemRepository.save(item));
    }

    @Override
    public Map<CategoryDto, List<ItemResponseDto>> getAll() {
        List<Item> items = itemRepository.findAll();
        List<Item> updatedItems = setStatus(items);

        Map<Long, List<ItemResponseDto>> itemsByCategoryId = updatedItems.stream()
                .flatMap(item -> item.getCategories().stream()
                        .map(category -> new AbstractMap.SimpleEntry<>(category.getId(),
                                ItemMapper.itemToItemResponseDto(item))))
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

        Map<CategoryDto, List<ItemResponseDto>> result = new HashMap<>();
        for (Map.Entry<Long, List<ItemResponseDto>> entry : itemsByCategoryId.entrySet()) {
            Long categoryId = entry.getKey();
            List<ItemResponseDto> itemDtos = entry.getValue();

            String categoryTitle = getCategoryTitleById(categoryId);

            CategoryDto categoryDto = CategoryDto.builder()
                    .id(categoryId)
                    .title(categoryTitle)
                    .build();

            result.put(categoryDto, itemDtos);
        }

        return result;
    }

    @Override
    public List<ItemResponseDto> getItemForCatId(Long catId) {

        categoryRepository.findById(catId)
                .orElseThrow(() -> new IdNotFoundException(String
                        .format("Извините такой категории с id = %d не существует", catId)));

        List<Item> items = itemRepository.getItemForCatId(catId);

        List<Item> updatedItems = setStatus(items);

        return updatedItems.stream()
                .map(ItemMapper::itemToItemResponseDto)
                .collect(Collectors.toList());
    }

    private List<Item> setStatus(List<Item> items) {
        for (Item item : items) {
            if (item.getTotalCount() > 5) {
                item.setItemStatus(ItemStatus.IN_STOCK);
            } else if (item.getTotalCount() > 0 && item.getTotalCount() <= 5) {
                item.setItemStatus(ItemStatus.A_LITTLE);
            } else if (item.getTotalCount() == 0) {
                item.setItemStatus(ItemStatus.OUT_OF_STOCK);
            } else {
                throw new LassThenZeroException("Извините произошла ошибка с нашей стороны, попробуйте позже");
            }
        }
        return itemRepository.saveAll(items);
    }

    private String getCategoryTitleById(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        return categoryOptional.map(Category::getTitle).orElse(null);
    }
}
