package etu.spb.nic.online.store.item.service;

import etu.spb.nic.online.store.category.dto.CategoryDto;
import etu.spb.nic.online.store.item.dto.ItemDto;

import java.util.List;
import java.util.Map;

public interface ItemService {

    List<ItemDto> getIphones();

    List<ItemDto> getSamsung();


    List<ItemDto> getPhones();

    Map<CategoryDto, List<ItemDto>> getAll();

    List<ItemDto> getAllAppleWatches();

    List<ItemDto> getWatches();

    List<ItemDto> getXiaomiWatches();

    List<ItemDto> getAllAudio();

    List<ItemDto> getAppleAirpods();

    List<ItemDto> getSmartSpeakers();

    List<ItemDto> getHeadphones();

    List<ItemDto> getSamsungHeadphones();

    List<ItemDto> getAppleCase();

    List<ItemDto> getAccessories();

    List<ItemDto> getSamsungCase();

}
