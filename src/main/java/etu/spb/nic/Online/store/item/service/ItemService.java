package etu.spb.nic.Online.store.item.service;

import etu.spb.nic.Online.store.item.dto.ItemDto;

import java.util.List;

public interface ItemService {

    List<ItemDto> getIphones();

    List<ItemDto> getSamsung();


    List<ItemDto> getPhones();

    List<ItemDto> getAll();

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

}
