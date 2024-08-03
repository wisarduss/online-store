package etu.spb.nic.online.store.category.controller;

import etu.spb.nic.online.store.category.dto.CategoryDto;
import etu.spb.nic.online.store.category.service.CategoryService;
import etu.spb.nic.online.store.item.dto.ItemResponseDto;
import etu.spb.nic.online.store.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalog")
public class CategoryController {

    private final ItemService itemService;
    private final CategoryService categoryService;

    @PostMapping
    public void addCategory(@RequestBody CategoryDto categoryDto) {
        categoryService.addCategory(categoryDto);
    }

    @GetMapping("/phones/iphone")
    public List<ItemResponseDto> getAllIphone() {
        return itemService.getIphones();
    }

    @GetMapping("/phones/samsung")
    public List<ItemResponseDto> getAllSamsung() {
        return itemService.getSamsung();
    }

    @GetMapping("/phones")
    public List<ItemResponseDto> getAllPhones() {
        return itemService.getPhones();
    }

    @GetMapping()
    public Map<CategoryDto, List<ItemResponseDto>> getAll() {
        return itemService.getAll();
    }

    @GetMapping("/watch/apple-watch")
    public List<ItemResponseDto> getAllAppleWatches() {
        return itemService.getAllAppleWatches();
    }

    @GetMapping("/watch")
    public List<ItemResponseDto> getAllWatches() {
        return itemService.getWatches();
    }

    @GetMapping("/watch/xiaomiwatch")
    public List<ItemResponseDto> getAllXiaomiWatches() {
        return itemService.getXiaomiWatches();
    }

    @GetMapping("/audio")
    public List<ItemResponseDto> getAllAudio() {
        return itemService.getAllAudio();
    }

    @GetMapping("/audio/headphones/airpods")
    public List<ItemResponseDto> getAppleAirpods() {
        return itemService.getAppleAirpods();
    }

    @GetMapping("/audio/smart-speakers")
    public List<ItemResponseDto> getSmartSpeakers() {
        return itemService.getSmartSpeakers();
    }

    @GetMapping("/audio/headphones")
    public List<ItemResponseDto> getHeadphones() {
        return itemService.getHeadphones();
    }

    @GetMapping("/audio/headphones/samsung")
    public List<ItemResponseDto> getSamsungHeadphones() {
        return itemService.getSamsungHeadphones();
    }

    @GetMapping("/accessories/for-phones/iphone")
    public List<ItemResponseDto> getAccessoriesIphone() {
        return itemService.getAppleCase();
    }

    @GetMapping("/accessories")
    public List<ItemResponseDto> getAllAccessories() {
        return itemService.getAccessories();
    }

    @GetMapping("/accessories/for-phones/samsung")
    public List<ItemResponseDto> getAccessoriesSamsung() {
        return itemService.getSamsungCase();
    }
}
