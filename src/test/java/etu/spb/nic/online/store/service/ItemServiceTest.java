package etu.spb.nic.online.store.service;

import etu.spb.nic.online.store.authentication.config.JWTFilter;
import etu.spb.nic.online.store.authentication.controller.AuthController;
import etu.spb.nic.online.store.authentication.service.AuthenticationService;
import etu.spb.nic.online.store.category.dto.CategoryDto;
import etu.spb.nic.online.store.category.mapper.CategoryMapper;
import etu.spb.nic.online.store.category.model.Category;
import etu.spb.nic.online.store.category.repository.CategoryRepository;
import etu.spb.nic.online.store.common.util.JWTUtil;
import etu.spb.nic.online.store.item.dto.ItemDto;
import etu.spb.nic.online.store.item.model.Item;
import etu.spb.nic.online.store.item.repository.ItemRepository;
import etu.spb.nic.online.store.item.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
@Profile("test")
public class ItemServiceTest {
    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JWTFilter jwtFilter;

    @MockBean
    private JWTUtil jwtUtil;

    @MockBean
    private AuthController authController;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemService itemService;

    @Test
    void addItem() {
        CategoryDto categoryDto = CategoryDto.builder()
                .id(1L)
                .title("iphone")
                .build();

        Category category = CategoryMapper.categoryDtoToCategory(categoryDto);

        Set<Long> catIds = new HashSet<>();
        Set<Category> categories = new HashSet<>();
        catIds.add(categoryDto.getId());
        categories.add(category);

        ItemDto itemDto = ItemDto.builder()
                .id(1L)
                .title("Iphone 15")
                .description("Новейший телефон от Apple")
                .photoURL("photo_url_iphone")
                .price(69999L)
                .totalCount(200L)
                .status("В наличии")
                .catIds(catIds)
                .build();

        Item item = Item.builder()
                .id(1L)
                .title("Iphone 15")
                .description("Новейший телефон от Apple")
                .photoURL("photo_url_iphone")
                .price(69999L)
                .totalCount(200L)
                .itemStatus("В наличии")
                .categories(categories)
                .build();

        when(categoryRepository.findById(anyLong()))
                .thenReturn(Optional.of(category));
        when(itemRepository.save(any()))
                .thenReturn(item);

        ItemDto result = itemService.addItem(itemDto);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(itemDto.getTitle());
        assertThat(result.getDescription()).isEqualTo(itemDto.getDescription());
    }
}
