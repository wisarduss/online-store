package etu.spb.nic.online.store.service;

import etu.spb.nic.online.store.authentication.config.JWTFilter;
import etu.spb.nic.online.store.authentication.controller.AuthController;
import etu.spb.nic.online.store.authentication.service.AuthenticationService;
import etu.spb.nic.online.store.category.dto.CategoryDto;
import etu.spb.nic.online.store.category.mapper.CategoryMapper;
import etu.spb.nic.online.store.category.model.Category;
import etu.spb.nic.online.store.category.repository.CategoryRepository;
import etu.spb.nic.online.store.category.service.CategoryService;
import etu.spb.nic.online.store.common.util.JWTUtil;
import etu.spb.nic.online.store.item.dto.ItemResponseDto;
import etu.spb.nic.online.store.item.model.Item;
import etu.spb.nic.online.store.item.model.ItemStatus;
import etu.spb.nic.online.store.item.repository.ItemRepository;
import etu.spb.nic.online.store.item.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
@Profile("test")
public class CategoryServiceTest {

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JWTFilter jwtFilter;

    @MockBean
    private JWTUtil jwtUtil;

    @MockBean
    private AuthController authController;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoryService categoryService;

    @Test
    @WithMockUser
    void addCategory() {
        Category category = Category.builder()
                .id(1L)
                .title("iphone")
                .build();

        when(categoryRepository.save(any()))
                .thenReturn(category);

        CategoryDto categoryDto = CategoryMapper.categoryToCategoryDto(category);

        CategoryDto result = categoryService.addCategory(categoryDto);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(categoryDto.getTitle());

    }

    @Test
    void getItemsForCatId() {
        CategoryDto categoryDto = CategoryDto.builder()
                .id(1L)
                .title("iphone")
                .build();

        Category category = CategoryMapper.categoryDtoToCategory(categoryDto);

        Set<Category> categories = new HashSet<>();
        categories.add(category);

        ItemResponseDto itemResponseDto = ItemResponseDto.builder()
                .id(1L)
                .title("Iphone 15")
                .description("Новейший телефон от Apple")
                .photoURL("photo_url_iphone")
                .price(BigDecimal.valueOf(69999L))
                .status(ItemStatus.IN_STOCK.getText())
                .build();

        Item item = Item.builder()
                .id(1L)
                .title("Iphone 15")
                .description("Новейший телефон от Apple")
                .photoURL("photo_url_iphone")
                .price(BigDecimal.valueOf(69999L))
                .totalCount(200L)
                .itemStatus(ItemStatus.IN_STOCK)
                .categories(categories)
                .build();

        when(categoryRepository.findById(anyLong()))
                .thenReturn(Optional.of(category));
        when(itemRepository.save(any()))
                .thenReturn(item);

        List<ItemResponseDto> result = itemService.getItemForCatId(categoryDto.getId());

        assertThat(result).isNotNull();
    }


}
