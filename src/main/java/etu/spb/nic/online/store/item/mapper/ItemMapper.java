package etu.spb.nic.online.store.item.mapper;

import etu.spb.nic.online.store.category.model.Category;
import etu.spb.nic.online.store.item.dto.ItemDto;
import etu.spb.nic.online.store.item.dto.ItemResponseDto;
import etu.spb.nic.online.store.item.model.Item;
import etu.spb.nic.online.store.item.model.ItemStatus;
import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class ItemMapper {

    public static ItemDto itemToItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .title(item.getTitle())
                .description(item.getDescription())
                .photoURL(item.getPhotoURL())
                .price(item.getPrice())
                .status(item.getItemStatus().getText())
                .build();
    }

    public static Item itemDtoToItemWithIds(ItemDto itemDto, Set<Category> categories) {
        return Item.builder()
                .id(itemDto.getId())
                .title(itemDto.getTitle())
                .description(itemDto.getDescription())
                .photoURL(itemDto.getPhotoURL())
                .price(itemDto.getPrice())
                .itemStatus(ItemStatus.valueOf(itemDto.getStatus()))
                .totalCount(itemDto.getTotalCount())
                .categories(categories)
                .build();
    }

    public static ItemResponseDto itemToItemResponseDto(Item item) {
        return ItemResponseDto.builder()
                .id(item.getId())
                .title(item.getTitle())
                .description(item.getDescription())
                .photoURL(item.getPhotoURL())
                .price(item.getPrice())
                .status(item.getItemStatus().getText())
                .build();
    }

}
