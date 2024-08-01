package etu.spb.nic.online.store.item.service;

import etu.spb.nic.online.store.category.dto.CategoryDto;
import etu.spb.nic.online.store.category.model.Category;
import etu.spb.nic.online.store.category.repository.CategoryRepository;
import etu.spb.nic.online.store.common.exception.LassThenZeroException;
import etu.spb.nic.online.store.item.repository.ItemRepository;
import etu.spb.nic.online.store.item.dto.ItemDto;
import etu.spb.nic.online.store.item.mapper.ItemMapper;
import etu.spb.nic.online.store.item.model.Item;
import etu.spb.nic.online.store.item.model.ItemStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public List<ItemDto> getIphones() {
        List<Item> items = itemRepository.findByCatIdIphone();

        List<Item> updatedItems = checkStatus(items);

        return updatedItems.stream()
                .map(ItemMapper::itemToItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> getSamsung() {
        List<Item> items = itemRepository.findByCatIdSamsung();

        List<Item> updatedItems = checkStatus(items);

        return updatedItems.stream()
                .map(ItemMapper::itemToItemDto)
                .collect(Collectors.toList());

    }

    @Override
    public List<ItemDto> getPhones() {

        List<Item> items = itemRepository.findByCatIdPhones();


        List<Item> updatedItems = checkStatus(items);

        return updatedItems.stream()
                .map(ItemMapper::itemToItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public Map<CategoryDto, List<ItemDto>> getAll() {
        List<Item> items = itemRepository.findAll();
        List<Item> updatedItems = checkStatus(items);

        Map<Long, List<ItemDto>> itemsByCategoryId = updatedItems.stream()
                .flatMap(item -> item.getCategories().stream()
                        .map(category -> new AbstractMap.SimpleEntry<>(category.getId(), ItemMapper.itemToItemDto(item))))
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

        Map<CategoryDto, List<ItemDto>> result = new HashMap<>();
        for (Map.Entry<Long, List<ItemDto>> entry : itemsByCategoryId.entrySet()) {
            Long categoryId = entry.getKey();
            List<ItemDto> itemDtos = entry.getValue();

            String categoryTitle = getCategoryTitleById(categoryId);

            CategoryDto categoryDto = CategoryDto.builder()
                    .id(categoryId)
                    .title(categoryTitle)
                    .build();

            // Добавляем в результат
            result.put(categoryDto, itemDtos);
        }

        return result;
    }



    @Override
    public List<ItemDto> getAllAppleWatches() {
        List<Item> items = itemRepository.findByCatIdAppleWatch();


        List<Item> updatedItems = checkStatus(items);

        return updatedItems.stream()
                .map(ItemMapper::itemToItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> getWatches() {

        List<Item> items = itemRepository.findByCatIdWatch();


        List<Item> updatedItems = checkStatus(items);

        return updatedItems.stream()
                .map(ItemMapper::itemToItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> getXiaomiWatches() {

        List<Item> items = itemRepository.findByCatIdXiaomiWatch();


        List<Item> updatedItems = checkStatus(items);

        return updatedItems.stream()
                .map(ItemMapper::itemToItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> getAllAudio() {

        List<Item> items = itemRepository.findByCatIdAudio();


        List<Item> updatedItems = checkStatus(items);

        return updatedItems.stream()
                .map(ItemMapper::itemToItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> getAppleAirpods() {

        List<Item> items = itemRepository.findByCatIdAppleAirpods();


        List<Item> updatedItems = checkStatus(items);

        return updatedItems.stream()
                .map(ItemMapper::itemToItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> getSmartSpeakers() {

        List<Item> items = itemRepository.findByCatIdSmartSpeakers();


        List<Item> updatedItems = checkStatus(items);

        return updatedItems.stream()
                .map(ItemMapper::itemToItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> getHeadphones() {

        List<Item> items = itemRepository.findByCatIdHeadphones();


        List<Item> updatedItems = checkStatus(items);

        return updatedItems.stream()
                .map(ItemMapper::itemToItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> getSamsungHeadphones() {

        List<Item> items = itemRepository.findByCatIdSamsungHeadphones();


        List<Item> updatedItems = checkStatus(items);

        return updatedItems.stream()
                .map(ItemMapper::itemToItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> getAppleCase() {

        List<Item> items = itemRepository.findByCatIdAppleCase();


        List<Item> updatedItems = checkStatus(items);

        return updatedItems.stream()
                .map(ItemMapper::itemToItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> getAccessories() {

        List<Item> items = itemRepository.findByCatIdAccessories();


        List<Item> updatedItems = checkStatus(items);

        return updatedItems.stream()
                .map(ItemMapper::itemToItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> getSamsungCase() {

        List<Item> items = itemRepository.findByCatIdSamsungCase();


        List<Item> updatedItems = checkStatus(items);

        return updatedItems.stream()
                .map(ItemMapper::itemToItemDto)
                .collect(Collectors.toList());
    }

    private List<Item> checkStatus(List<Item> items) {
        for (Item item : items) {
            if (item.getTotalCount() > 5) {
                item.setItemStatus(ItemStatus.IN_STOCK.getText());
            } else if (item.getTotalCount() > 0 && item.getTotalCount() <= 5) {
                item.setItemStatus(ItemStatus.A_LITTLE.getText());
            } else if (item.getTotalCount() == 0) {
                item.setItemStatus(ItemStatus.OUT_OF_STOCK.getText());
            } else {
                throw new LassThenZeroException("Извините произошла ошибка с нашей стороны, попробуйте позже");
            }
        }
        return itemRepository.saveAll(items);
    }

    private String getCategoryTitleById(Long categoryId) {
        // Находим категорию по ID
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        // Если категория найдена, возвращаем её заголовок, иначе возвращаем null или пустую строку
        return categoryOptional.map(Category::getTitle).orElse(null);
    }
}
