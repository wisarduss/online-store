package etu.spb.nic.online.store.item.service;

import etu.spb.nic.online.store.category.dto.CategoryDto;
import etu.spb.nic.online.store.item.dto.ItemDto;
import etu.spb.nic.online.store.item.dto.ItemResponseDto;

import java.util.List;
import java.util.Map;

public interface ItemService {

    List<ItemResponseDto> getIphones();

    List<ItemResponseDto> getSamsung();

    void addItem(ItemDto itemDto);

    List<ItemResponseDto> getPhones();

    Map<CategoryDto, List<ItemResponseDto>> getAll();

    List<ItemResponseDto> getAllAppleWatches();

    List<ItemResponseDto> getWatches();

    List<ItemResponseDto> getXiaomiWatches();

    List<ItemResponseDto> getAllAudio();

    List<ItemResponseDto> getAppleAirpods();

    List<ItemResponseDto> getSmartSpeakers();

    List<ItemResponseDto> getHeadphones();

    List<ItemResponseDto> getSamsungHeadphones();

    List<ItemResponseDto> getAppleCase();

    List<ItemResponseDto> getAccessories();

    List<ItemResponseDto> getSamsungCase();

}
