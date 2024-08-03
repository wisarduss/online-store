package etu.spb.nic.Online.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import etu.spb.nic.online.store.category.controller.CategoryController;
import etu.spb.nic.online.store.category.dto.CategoryDto;
import etu.spb.nic.online.store.category.model.Category;
import etu.spb.nic.online.store.common.exception.ErrorHandler;
import etu.spb.nic.online.store.item.dto.ItemResponseDto;
import etu.spb.nic.online.store.item.mapper.ItemMapper;
import etu.spb.nic.online.store.item.model.Item;
import etu.spb.nic.online.store.item.model.ItemStatus;
import etu.spb.nic.online.store.item.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryController.class)
@ContextConfiguration(classes = {CategoryController.class, ErrorHandler.class})
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemService itemService;


    private String URL;

    @BeforeEach
    void setUp() {
        URL = "http://localhost:8080/catalog";
    }

    @Test
    @WithMockUser
    void getIPhones() throws Exception {
        Category category = Category.builder()
                .id(1L)
                .title("iphone")
                .build();

        Set<Category> categories = new HashSet<>();

        categories.add(category);


        Item item = Item.builder()
                .id(1L)
                .title("iphone 15 pro")
                .description("мощный телефон")
                .photoURL("photo_url")
                .price(89999L)
                .totalCount(345L)
                .itemStatus(ItemStatus.IN_STOCK.getText())
                .categories(categories)
                .build();

        ItemResponseDto result = ItemMapper.itemToItemResponseDto(item);

        List<ItemResponseDto> items = new ArrayList<>();

        items.add(result);


        when(itemService.getIphones())
                .thenReturn(items);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get(URL.concat("/phones/iphone")));

        response.andExpect(status().isOk());
        verify(itemService, times(1)).getIphones();
        assertEquals(itemService.getIphones().size(), 1);
    }

    @Test
    @WithMockUser
    void getSamsung() throws Exception {
        Category category = Category.builder()
                .id(1L)
                .title("samsung")
                .build();

        Set<Category> categories = new HashSet<>();

        categories.add(category);


        Item item = Item.builder()
                .id(1L)
                .title("samsung a32")
                .description("мощный телефон")
                .photoURL("photo_url")
                .price(25499L)
                .totalCount(200L)
                .itemStatus(ItemStatus.IN_STOCK.getText())
                .categories(categories)
                .build();

        ItemResponseDto result = ItemMapper.itemToItemResponseDto(item);

        List<ItemResponseDto> items = new ArrayList<>();

        items.add(result);


        when(itemService.getSamsung())
                .thenReturn(items);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get(URL.concat("/phones/samsung")));

        response.andExpect(status().isOk());
        verify(itemService, times(1)).getSamsung();
        assertEquals(itemService.getSamsung().size(), 1);
    }

    @Test
    @WithMockUser
    void getPhones() throws Exception {
        Category categoryIphone = Category.builder()
                .id(1L)
                .title("iphone")
                .build();

        Category categoryPhones = Category.builder()
                .id(1L)
                .title("phone")
                .build();

        Category categorySamsung = Category.builder()
                .id(1L)
                .title("samsung")
                .build();

        Set<Category> categoriesForIphone = new HashSet<>();
        Set<Category> categoriesForSamsung = new HashSet<>();

        categoriesForIphone.add(categoryIphone);
        categoriesForIphone.add(categoryPhones);

        categoriesForSamsung.add(categoryPhones);
        categoriesForSamsung.add(categorySamsung);


        Item itemIphone = Item.builder()
                .id(1L)
                .title("iphone 15 pro")
                .description("мощный телефон")
                .photoURL("photo_url")
                .price(89999L)
                .totalCount(345L)
                .itemStatus(ItemStatus.IN_STOCK.getText())
                .categories(categoriesForIphone)
                .build();

        Item itemSamsung = Item.builder()
                .id(2L)
                .title("samsung a32")
                .description("мощный телефон")
                .photoURL("photo_url")
                .price(25999L)
                .totalCount(200L)
                .itemStatus(ItemStatus.IN_STOCK.getText())
                .categories(categoriesForSamsung)
                .build();

        ItemResponseDto result = ItemMapper.itemToItemResponseDto(itemIphone);
        ItemResponseDto secondResult = ItemMapper.itemToItemResponseDto(itemSamsung);

        List<ItemResponseDto> items = new ArrayList<>();

        items.add(result);
        items.add(secondResult);

        when(itemService.getPhones())
                .thenReturn(items);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get(URL.concat("/phones")));

        response.andExpect(status().isOk());
        verify(itemService, times(1)).getPhones();
        assertEquals(itemService.getPhones().size(), 2);
    }


    @Test
    @WithMockUser
    void getAll() throws Exception {
        Category categoryIphone = Category.builder()
                .id(1L)
                .title("iphone")
                .build();

        Category categoryPhones = Category.builder()
                .id(1L)
                .title("phone")
                .build();

        Category categorySamsung = Category.builder()
                .id(1L)
                .title("samsung")
                .build();

        Set<Category> categoriesForIphone = new HashSet<>();
        Set<Category> categoriesForSamsung = new HashSet<>();

        categoriesForIphone.add(categoryIphone);
        categoriesForIphone.add(categoryPhones);

        categoriesForSamsung.add(categoryPhones);
        categoriesForSamsung.add(categorySamsung);

        Item itemIphone = Item.builder()
                .id(1L)
                .title("iphone 15 pro")
                .description("мощный телефон")
                .photoURL("photo_url")
                .price(89999L)
                .totalCount(345L)
                .itemStatus(ItemStatus.IN_STOCK.getText())
                .categories(categoriesForIphone)
                .build();

        Item itemSamsung = Item.builder()
                .id(2L)
                .title("samsung a32")
                .description("мощный телефон")
                .photoURL("photo_url")
                .price(25999L)
                .totalCount(200L)
                .itemStatus(ItemStatus.IN_STOCK.getText())
                .categories(categoriesForSamsung)
                .build();

        ItemResponseDto result = ItemMapper.itemToItemResponseDto(itemIphone);
        ItemResponseDto secondResult = ItemMapper.itemToItemResponseDto(itemSamsung);

        List<ItemResponseDto> itemsIphone = new ArrayList<>();
        List<ItemResponseDto> itemsSamsung = new ArrayList<>();
        List<ItemResponseDto> itemsPhones = new ArrayList<>();

        itemsIphone.add(result);

        itemsSamsung.add(secondResult);

        itemsPhones.add(result);
        itemsPhones.add(secondResult);

        CategoryDto categoryDtoIphone = CategoryDto.builder()
                .id(categoryIphone.getId())
                .title(categoryIphone.getTitle())
                .build();

        CategoryDto categoryDtoSamsung = CategoryDto.builder()
                .id(categorySamsung.getId())
                .title(categorySamsung.getTitle())
                .build();

        CategoryDto categoryDtoPhones = CategoryDto.builder()
                .id(categoryPhones.getId())
                .title(categoryPhones.getTitle())
                .build();

        Map<CategoryDto, List<ItemResponseDto>> itemsMap = new HashMap<>();

        itemsMap.put(categoryDtoIphone, itemsIphone);
        itemsMap.put(categoryDtoSamsung, itemsSamsung);
        itemsMap.put(categoryDtoPhones, itemsPhones);

        when(itemService.getAll())
                .thenReturn(itemsMap);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get(URL));

        response.andExpect(status().isOk());
        verify(itemService, times(1)).getAll();
        assertEquals(itemService.getAll().size(), 3);
    }

    @Test
    @WithMockUser
    void getXiaomiWatch() throws Exception {
        Category category = Category.builder()
                .id(1L)
                .title("xiaomi")
                .build();

        Set<Category> categories = new HashSet<>();

        categories.add(category);

        Item item = Item.builder()
                .id(1L)
                .title("mi band 7")
                .description("мощные часы")
                .photoURL("photo_url")
                .price(5999L)
                .totalCount(50L)
                .itemStatus(ItemStatus.IN_STOCK.getText())
                .categories(categories)
                .build();

        List<ItemResponseDto> items = new ArrayList<>();

        ItemResponseDto result = ItemMapper.itemToItemResponseDto(item);
        items.add(result);

        when(itemService.getXiaomiWatches())
                .thenReturn(items);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get(URL.concat("/watch/xiaomiwatch")));

        response.andExpect(status().isOk());
        verify(itemService, times(1)).getXiaomiWatches();
        assertEquals(itemService.getXiaomiWatches().size(), 1);

    }

    @Test
    @WithMockUser
    void getWatch() throws Exception {
        Category categoryXiaomi = Category.builder()
                .id(1L)
                .title("xiaomi")
                .build();

        Category categoryAirpods = Category.builder()
                .id(1L)
                .title("applewatch")
                .build();

        Set<Category> categories = new HashSet<>();

        categories.add(categoryXiaomi);
        categories.add(categoryAirpods);

        Item itemXiaomi = Item.builder()
                .id(1L)
                .title("mi band 7")
                .description("мощные часы")
                .photoURL("photo_url")
                .price(5999L)
                .totalCount(50L)
                .itemStatus(ItemStatus.IN_STOCK.getText())
                .categories(categories)
                .build();

        Item itemAirpods = Item.builder()
                .id(1L)
                .title("applewatch se 2023")
                .description("мощные часы от apple")
                .photoURL("photo_url")
                .price(22390L)
                .totalCount(500L)
                .itemStatus(ItemStatus.IN_STOCK.getText())
                .categories(categories)
                .build();

        List<ItemResponseDto> items = new ArrayList<>();

        ItemResponseDto result = ItemMapper.itemToItemResponseDto(itemXiaomi);

        ItemResponseDto secondResult = ItemMapper.itemToItemResponseDto(itemAirpods);
        items.add(result);
        items.add(secondResult);

        when(itemService.getWatches())
                .thenReturn(items);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get(URL.concat("/watch")));

        response.andExpect(status().isOk());
        verify(itemService, times(1)).getWatches();
        assertEquals(itemService.getWatches().size(), 2);
    }

    @Test
    @WithMockUser
    void getAppleWatch() throws Exception {

        Category category = Category.builder()
                .id(1L)
                .title("applewatch")
                .build();

        Set<Category> categories = new HashSet<>();

        categories.add(category);

        Item item = Item.builder()
                .id(1L)
                .title("applewatch se 2023")
                .description("мощные часы")
                .photoURL("photo_url")
                .price(23599L)
                .totalCount(50L)
                .itemStatus(ItemStatus.IN_STOCK.getText())
                .categories(categories)
                .build();

        List<ItemResponseDto> items = new ArrayList<>();

        ItemResponseDto result = ItemMapper.itemToItemResponseDto(item);
        items.add(result);

        when(itemService.getAllAppleWatches())
                .thenReturn(items);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get(URL.concat("/watch/apple-watch")));

        response.andExpect(status().isOk());
        verify(itemService, times(1)).getAllAppleWatches();
        assertEquals(itemService.getAllAppleWatches().size(), 1);
    }

    @Test
    @WithMockUser
    void getAudio() throws Exception {

        Category categoryAppleAirpods = Category.builder()
                .id(1L)
                .title("airpods")
                .build();

        Category categoryYandexAlisa = Category.builder()
                .id(1L)
                .title("alisa")
                .build();

        Category categorySamsungAkg = Category.builder()
                .id(1L)
                .title("samsungHeadphones")
                .build();

        Set<Category> categoriesAirpods = new HashSet<>();
        Set<Category> categoriesAlisa = new HashSet<>();
        Set<Category> categoriesSamsungHeadphones = new HashSet<>();

        categoriesAirpods.add(categoryAppleAirpods);

        categoriesSamsungHeadphones.add(categorySamsungAkg);

        categoriesAlisa.add(categoryYandexAlisa);

        Item itemAirpods = Item.builder()
                .id(1L)
                .title("apple airpods pro 2")
                .description("мощные наушники от apple")
                .photoURL("photo_url")
                .price(23599L)
                .totalCount(50L)
                .itemStatus(ItemStatus.IN_STOCK.getText())
                .categories(categoriesAirpods)
                .build();

        Item itemSamsungHeadphones = Item.builder()
                .id(1L)
                .title("akg")
                .description("проводные наушники от samsung")
                .photoURL("photo_url")
                .price(2000L)
                .totalCount(50L)
                .itemStatus(ItemStatus.IN_STOCK.getText())
                .categories(categoriesSamsungHeadphones)
                .build();

        Item itemAlisa = Item.builder()
                .id(1L)
                .title("alisa mini")
                .description("громкая колонка от yandex")
                .photoURL("photo_url")
                .price(6579L)
                .totalCount(300L)
                .itemStatus(ItemStatus.IN_STOCK.getText())
                .categories(categoriesAlisa)
                .build();

        ItemResponseDto result = ItemMapper.itemToItemResponseDto(itemAirpods);

        ItemResponseDto secondResult = ItemMapper.itemToItemResponseDto(itemSamsungHeadphones);

        ItemResponseDto thirdResult = ItemMapper.itemToItemResponseDto(itemAlisa);

        List<ItemResponseDto> items = new ArrayList<>();

        items.add(result);
        items.add(secondResult);
        items.add(thirdResult);

        when(itemService.getAllAudio())
                .thenReturn(items);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get(URL.concat("/audio")));

        response.andExpect(status().isOk());
        verify(itemService, times(1)).getAllAudio();
        assertEquals(itemService.getAllAudio().size(), 3);
    }

    @Test
    @WithMockUser
    void getAirpods() throws Exception {
        Category categoryAppleAirpods = Category.builder()
                .id(1L)
                .title("airpods")
                .build();

        Set<Category> categoriesAirpods = new HashSet<>();

        categoriesAirpods.add(categoryAppleAirpods);

        Item itemAirpods = Item.builder()
                .id(1L)
                .title("apple airpods pro 2")
                .description("мощные наушники от apple")
                .photoURL("photo_url")
                .price(23599L)
                .totalCount(50L)
                .itemStatus(ItemStatus.IN_STOCK.getText())
                .categories(categoriesAirpods)
                .build();


        List<ItemResponseDto> items = new ArrayList<>();

        ItemResponseDto result = ItemMapper.itemToItemResponseDto(itemAirpods);

        items.add(result);

        when(itemService.getAppleAirpods())
                .thenReturn(items);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get(URL.concat("/audio/headphones/airpods")));

        response.andExpect(status().isOk());
        verify(itemService, times(1)).getAppleAirpods();
        assertEquals(itemService.getAppleAirpods().size(), 1);
    }

    @Test
    @WithMockUser
    void getSmartSpeakers() throws Exception {
        Category categoryAlisa = Category.builder()
                .id(1L)
                .title("alisa")
                .build();

        Set<Category> categoriesAlisa = new HashSet<>();

        categoriesAlisa.add(categoryAlisa);

        Item itemAirpods = Item.builder()
                .id(1L)
                .title("alisa mini")
                .description("умная колонка от yandex")
                .photoURL("photo_url")
                .price(6999L)
                .totalCount(50L)
                .itemStatus(ItemStatus.IN_STOCK.getText())
                .categories(categoriesAlisa)
                .build();

        List<ItemResponseDto> items = new ArrayList<>();

        ItemResponseDto result = ItemMapper.itemToItemResponseDto(itemAirpods);

        items.add(result);

        when(itemService.getSmartSpeakers())
                .thenReturn(items);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get(URL.concat("/audio/smart-speakers")));

        response.andExpect(status().isOk());
        verify(itemService, times(1)).getSmartSpeakers();
        assertEquals(itemService.getSmartSpeakers().size(), 1);
    }

    @Test
    @WithMockUser
    void getHeadphones() throws Exception {

        Category categoryAppleAirpods = Category.builder()
                .id(1L)
                .title("airpods")
                .build();

        Category categorySamsungAkg = Category.builder()
                .id(1L)
                .title("samsungHeadphones")
                .build();

        Set<Category> categoriesAirpods = new HashSet<>();
        Set<Category> categoriesSamsungHeadphones = new HashSet<>();

        categoriesAirpods.add(categoryAppleAirpods);
        categoriesSamsungHeadphones.add(categorySamsungAkg);

        Item itemAirpods = Item.builder()
                .id(1L)
                .title("apple airpods pro 2")
                .description("мощные наушники от apple")
                .photoURL("photo_url")
                .price(23599L)
                .totalCount(50L)
                .itemStatus(ItemStatus.IN_STOCK.getText())
                .categories(categoriesAirpods)
                .build();

        Item itemSamsungHeadphones = Item.builder()
                .id(1L)
                .title("akg")
                .description("проводные наушники от samsung")
                .photoURL("photo_url")
                .price(2000L)
                .totalCount(50L)
                .itemStatus(ItemStatus.IN_STOCK.getText())
                .categories(categoriesSamsungHeadphones)
                .build();

        List<ItemResponseDto> items = new ArrayList<>();

        ItemResponseDto result = ItemMapper.itemToItemResponseDto(itemAirpods);
        ItemResponseDto secondResult = ItemMapper.itemToItemResponseDto(itemSamsungHeadphones);

        items.add(result);
        items.add(secondResult);

        when(itemService.getHeadphones())
                .thenReturn(items);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get(URL.concat("/audio/headphones")));

        response.andExpect(status().isOk());
        verify(itemService, times(1)).getHeadphones();
        assertEquals(itemService.getHeadphones().size(), 2);
    }

    @Test
    @WithMockUser
    void getSamsungHeadphones() throws Exception {
        Category categorySamsungHeadphones = Category.builder()
                .id(1L)
                .title("samsungHeadphones")
                .build();

        Set<Category> categoriesSamsung = new HashSet<>();

        categoriesSamsung.add(categorySamsungHeadphones);

        Item itemSamsungHeadphones = Item.builder()
                .id(1L)
                .title("akg")
                .description("проводные наушники от samsung")
                .photoURL("photo_url")
                .price(2000L)
                .totalCount(50L)
                .itemStatus(ItemStatus.IN_STOCK.getText())
                .categories(categoriesSamsung)
                .build();


        List<ItemResponseDto> items = new ArrayList<>();

        ItemResponseDto result = ItemMapper.itemToItemResponseDto(itemSamsungHeadphones);

        items.add(result);

        when(itemService.getSamsungHeadphones())
                .thenReturn(items);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get(URL.concat("/audio/headphones/samsung")));

        response.andExpect(status().isOk());
        verify(itemService, times(1)).getSamsungHeadphones();
        assertEquals(itemService.getSamsungHeadphones().size(), 1);
    }

    @Test
    @WithMockUser
    void getAccessoriesForIphone() throws Exception {
        Category category = Category.builder()
                .id(1L)
                .title("AppleCase")
                .build();

        Set<Category> categories = new HashSet<>();

        categories.add(category);

        Item item = Item.builder()
                .id(1L)
                .title("case")
                .description("силиконовый чехол для Iphone 15")
                .photoURL("photo_url")
                .price(799L)
                .totalCount(30L)
                .itemStatus(ItemStatus.IN_STOCK.getText())
                .categories(categories)
                .build();

        List<ItemResponseDto> items = new ArrayList<>();

        ItemResponseDto result = ItemMapper.itemToItemResponseDto(item);

        items.add(result);

        when(itemService.getAppleCase())
                .thenReturn(items);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get(URL.concat("/accessories/for-phones/iphone")));

        response.andExpect(status().isOk());
        verify(itemService, times(1)).getAppleCase();
        assertEquals(itemService.getAppleCase().size(), 1);

    }

    @Test
    @WithMockUser
    void getAccessories() throws Exception {
        Category categoryAppleCase = Category.builder()
                .id(1L)
                .title("AppleCase")
                .build();

        Category categorySamsungCase = Category.builder()
                .id(2L)
                .title("SamsungCase")
                .build();

        Set<Category> categoriesAppleCase = new HashSet<>();
        Set<Category> categoriesSamsungCase = new HashSet<>();

        categoriesSamsungCase.add(categoryAppleCase);
        categoriesSamsungCase.add(categorySamsungCase);

        Item itemAppleCase = Item.builder()
                .id(1L)
                .title("case")
                .description("силиконовый чехол для Iphone 15")
                .photoURL("photo_url")
                .price(799L)
                .totalCount(30L)
                .itemStatus(ItemStatus.IN_STOCK.getText())
                .categories(categoriesAppleCase)
                .build();

        Item itemSamsungCase = Item.builder()
                .id(1L)
                .title("samsung case")
                .description("силиконовый чехол для samsung a32")
                .photoURL("photo_url")
                .price(299L)
                .totalCount(30L)
                .itemStatus(ItemStatus.IN_STOCK.getText())
                .categories(categoriesSamsungCase)
                .build();

        List<ItemResponseDto> items = new ArrayList<>();

        ItemResponseDto result = ItemMapper.itemToItemResponseDto(itemAppleCase);
        ItemResponseDto secondResult = ItemMapper.itemToItemResponseDto(itemSamsungCase);

        items.add(result);
        items.add(secondResult);

        when(itemService.getAccessories())
                .thenReturn(items);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get(URL.concat("/accessories")));

        response.andExpect(status().isOk());
        verify(itemService, times(1)).getAccessories();
        assertEquals(itemService.getAccessories().size(), 2);
    }


    @Test
    @WithMockUser
    void getSamsungCase() throws Exception {
        Category category = Category.builder()
                .id(1L)
                .title("samsungCase")
                .build();

        Set<Category> categories = new HashSet<>();

        categories.add(category);

        Item item = Item.builder()
                .id(1L)
                .title("samsung case")
                .description("силиконовый чехол для samsung a32")
                .photoURL("photo_url")
                .price(299L)
                .totalCount(30L)
                .itemStatus(ItemStatus.IN_STOCK.getText())
                .categories(categories)
                .build();

        List<ItemResponseDto> items = new ArrayList<>();

        ItemResponseDto result = ItemMapper.itemToItemResponseDto(item);

        items.add(result);

        when(itemService.getSamsungCase())
                .thenReturn(items);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get(URL.concat("/accessories/for-phones/samsung")));

        response.andExpect(status().isOk());
        verify(itemService, times(1)).getSamsungCase();
        assertEquals(itemService.getSamsungCase().size(), 1);

    }

}
