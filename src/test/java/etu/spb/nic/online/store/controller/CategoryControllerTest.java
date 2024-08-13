package etu.spb.nic.online.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import etu.spb.nic.online.store.category.controller.CategoryController;
import etu.spb.nic.online.store.category.dto.CategoryDto;
import etu.spb.nic.online.store.category.model.Category;
import etu.spb.nic.online.store.category.service.CategoryService;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private WebApplicationContext context;

    private String URL;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        URL = "http://localhost:8080/catalog";
    }

    @Test
    @WithMockUser
    void addCategory() throws Exception {
        CategoryDto categoryDto = CategoryDto.builder()
                .id(1L)
                .title("iphone")
                .build();

        when(categoryService.addCategory(any()))
                .thenReturn(categoryDto);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(categoryDto);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));

        response.andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getItemsForCatId() throws Exception {
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
                .price(BigDecimal.valueOf(89999L))
                .totalCount(345L)
                .itemStatus(ItemStatus.IN_STOCK)
                .categories(categories)
                .build();

        ItemResponseDto result = ItemMapper.itemToItemResponseDto(item);

        List<ItemResponseDto> items = new ArrayList<>();

        items.add(result);

        when(itemService.getItemForCatId(category.getId()))
                .thenReturn(items);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get(URL.concat("/{catId}"), 1L));

        response.andExpect(status().isOk());
        verify(itemService, times(1)).getItemForCatId(1L);
        assertEquals(itemService.getItemForCatId(1L).size(), 1);
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
                .price(BigDecimal.valueOf(89999L))
                .totalCount(345L)
                .itemStatus(ItemStatus.IN_STOCK)
                .categories(categoriesForIphone)
                .build();

        Item itemSamsung = Item.builder()
                .id(2L)
                .title("samsung a32")
                .description("мощный телефон")
                .photoURL("photo_url")
                .price(BigDecimal.valueOf(25999L))
                .totalCount(200L)
                .itemStatus(ItemStatus.IN_STOCK)
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

}
