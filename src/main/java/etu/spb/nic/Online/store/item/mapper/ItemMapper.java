package etu.spb.nic.Online.store.item.mapper;

import etu.spb.nic.Online.store.item.dto.ItemDto;
import etu.spb.nic.Online.store.item.model.Item;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ItemMapper {

    public static ItemDto itemToItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .title(item.getTitle())
                .description(item.getDescription())
                .photoURL(item.getPhotoURL())
                .price(item.getPrice())
                .status(item.getItemStatus())
                .build();
    }
}
