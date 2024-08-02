package etu.spb.nic.Online.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import etu.spb.nic.online.store.cart.controller.CartController;
import etu.spb.nic.online.store.cart.dto.CartDto;
import etu.spb.nic.online.store.cart.mapper.CartMapper;
import etu.spb.nic.online.store.cart.model.Cart;
import etu.spb.nic.online.store.cart.repository.CartRepository;
import etu.spb.nic.online.store.cart.service.CartService;
import etu.spb.nic.online.store.category.model.Category;
import etu.spb.nic.online.store.common.exception.ErrorHandler;
import etu.spb.nic.online.store.item.model.Item;
import etu.spb.nic.online.store.item.model.ItemStatus;
import etu.spb.nic.online.store.item.repository.ItemRepository;
import etu.spb.nic.online.store.user.model.User;
import etu.spb.nic.online.store.user.service.UserService;
import lombok.With;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = CartController.class)
@ContextConfiguration(classes = {CartController.class, ErrorHandler.class})
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CartService cartService;

    private String URL;


    @BeforeEach
    void setUp() {
        URL = "http://localhost:8080/cart";
    }


    @Test
    @WithMockUser
    void getCart() throws Exception {
        Category category = Category.builder()
                .id(1L)
                .title("iphone")
                .build();

        Set<Category> categories = new HashSet<>();

        categories.add(category);
        User user = User.builder()
                .id(1L)
                .name("Maxim")
                .surname("Borodulin")
                .email("Max@mail.ru")
                .password("12345")
                .build();

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

        Set<Long> items = new HashSet<>();
        items.add(item.getId());

        Cart cart = Cart.builder()
                .id(1L)
                .user(user)
                .build();

        CartDto result = CartMapper.cartToCartDto(cart, items);

        when(cartService.getCartForUser())
                .thenReturn(result);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get(URL));

        response.andExpect(status().isOk());
        verify(cartService, times(1)).getCartForUser();
    }


}
