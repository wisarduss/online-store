package etu.spb.nic.online.store.item.service;

import etu.spb.nic.online.store.common.exception.LassThenZeroException;
import etu.spb.nic.online.store.item.ItemRepository;
import etu.spb.nic.online.store.item.dto.ItemDto;
import etu.spb.nic.online.store.item.mapper.ItemMapper;
import etu.spb.nic.online.store.item.model.Item;
import etu.spb.nic.online.store.item.model.ItemStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;


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
    public List<ItemDto> getAll() {
        List<Item> items = itemRepository.findAll();

        List<Item> updatedItems = checkStatus(items);

        return updatedItems.stream()
                .map(ItemMapper::itemToItemDto)
                .collect(Collectors.toList());
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
            itemRepository.save(item);
        }
        return items;
    }
}
