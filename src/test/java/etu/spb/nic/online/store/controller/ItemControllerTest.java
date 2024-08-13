package etu.spb.nic.online.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import etu.spb.nic.online.store.category.dto.CategoryDto;
import etu.spb.nic.online.store.common.exception.ErrorHandler;
import etu.spb.nic.online.store.item.controller.ItemController;
import etu.spb.nic.online.store.item.dto.ItemDto;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemController.class)
@ContextConfiguration(classes = {ItemController.class, ErrorHandler.class})
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemService itemService;

    @Autowired
    private WebApplicationContext context;

    private String URL;


    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        URL = "http://localhost:8080/items";
    }

    @Test
    @WithMockUser
    void addItem() throws Exception {
        CategoryDto categoryDto = CategoryDto.builder()
                .id(1L)
                .title("iphone")
                .build();

        Set<Long> catIds = new HashSet<>();
        catIds.add(categoryDto.getId());

        ItemDto itemDto = ItemDto.builder()
                .id(1L)
                .title("Iphone 15")
                .description("Новейший телефон от Apple")
                .photoURL("photo_url_iphone")
                .price(BigDecimal.valueOf(69999))
                .totalCount(200L)
                .status(ItemStatus.IN_STOCK)
                .catIds(catIds)
                .build();

        when(itemService.addItem(any()))
                .thenReturn(itemDto);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(itemDto);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .header("Content-Type", "application/json")
                .content(jsonRequest));

        response.andExpect(status().isOk());
    }

}
