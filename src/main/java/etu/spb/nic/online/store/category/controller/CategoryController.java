package etu.spb.nic.online.store.category.controller;

import etu.spb.nic.online.store.category.dto.CategoryDto;
import etu.spb.nic.online.store.item.dto.ItemDto;
import etu.spb.nic.online.store.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalog")
public class CategoryController {

    private final ItemService itemService;

    @GetMapping("/phones/iphone")
    public List<ItemDto> getAllIphone() {
        return itemService.getIphones();
    }

    @GetMapping("/phones/samsung")
    public List<ItemDto> getAllSamsung() {
        return itemService.getSamsung();
    }

    @GetMapping("/phones")
    public List<ItemDto> getAllPhones() {
        return itemService.getPhones();
    }

    @GetMapping()
    public Map<CategoryDto, List<ItemDto>> getAll() {
        return itemService.getAll();
    }

    @GetMapping("/watch/apple-watch")
    public List<ItemDto> getAllAppleWatches() {
        return itemService.getAllAppleWatches();
    }

    @GetMapping("/watch")
    public List<ItemDto> getAllWatches() {
        return itemService.getWatches();
    }

    @GetMapping("/watch/xiaomiwatch")
    public List<ItemDto> getAllXiaomiWatches() {
        return itemService.getXiaomiWatches();
    }

    @GetMapping("/audio")
    public List<ItemDto> getAllAudio() {
        return itemService.getAllAudio();
    }

    @GetMapping("/audio/headphones/airpods")
    public List<ItemDto> getAppleAirpods() {
        return itemService.getAppleAirpods();
    }

    @GetMapping("/audio/smart-speakers")
    public List<ItemDto> getSmartSpeakers() {
        return itemService.getSmartSpeakers();
    }

    @GetMapping("/audio/headphones")
    public List<ItemDto> getHeadphones() {
        return itemService.getHeadphones();
    }

    @GetMapping("/audio/headphones/samsung")
    public List<ItemDto> getSamsungHeadphones() {
        return itemService.getSamsungHeadphones();
    }

    @GetMapping("/accessories/for-phones/iphone")
    public List<ItemDto> getAccessoriesIphone() {
        return itemService.getAppleCase();
    }

    @GetMapping("/accessories")
    public List<ItemDto> getAllAccessories() {
        return itemService.getAccessories();
    }

    @GetMapping("/accessories/for-phones/samsung")
    public List<ItemDto> getAccessoriesSamsung() {
        return itemService.getSamsungCase();
    }
}
