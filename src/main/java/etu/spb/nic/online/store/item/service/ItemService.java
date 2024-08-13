package etu.spb.nic.online.store.item.service;

import etu.spb.nic.online.store.category.dto.CategoryDto;
import etu.spb.nic.online.store.item.dto.ItemDto;
import etu.spb.nic.online.store.item.dto.ItemResponseDto;

import java.util.List;
import java.util.Map;

public interface ItemService {

    Map<CategoryDto, List<ItemResponseDto>> getAll();

    ItemDto addItem(ItemDto itemDto);

    List<ItemResponseDto> getItemForCatId(Long catId);

}
