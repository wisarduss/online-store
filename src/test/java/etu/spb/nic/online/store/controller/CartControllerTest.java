package etu.spb.nic.online.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import etu.spb.nic.online.store.authentication.security.PersonDetails;
import etu.spb.nic.online.store.cart.controller.CartController;
import etu.spb.nic.online.store.cart.dto.CartDto;
import etu.spb.nic.online.store.cart.mapper.CartMapper;
import etu.spb.nic.online.store.cart.model.Cart;
import etu.spb.nic.online.store.cart.service.CartService;
import etu.spb.nic.online.store.category.model.Category;
import etu.spb.nic.online.store.common.exception.ErrorHandler;
import etu.spb.nic.online.store.item.model.Item;
import etu.spb.nic.online.store.item.model.ItemStatus;
import etu.spb.nic.online.store.item.repository.ItemRepository;
import etu.spb.nic.online.store.user.model.User;
import etu.spb.nic.online.store.user.repository.UserRepository;
import etu.spb.nic.online.store.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
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

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ItemRepository itemRepository;

    @Autowired
    private WebApplicationContext context;

    private String URL;

    private User authenticatedUser;


    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        URL = "http://localhost:8080/cart";
        authenticatedUser = new User();
        authenticatedUser.setId(1L);
        authenticatedUser.setEmail("test@example.com");

        PersonDetails personDetails = new PersonDetails(authenticatedUser);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(personDetails);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
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

    @Test
    @WithMockUser
    void addItemToCart() throws Exception {

        when(userRepository.findByEmail(authenticatedUser.getEmail()))
                .thenReturn(Optional.of(authenticatedUser));

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

        when(itemRepository.findById(anyLong()))
                .thenReturn(Optional.of(item));

        Set<Long> items = new HashSet<>();
        items.add(item.getId());

        Cart cart = Cart.builder()
                .id(1L)
                .user(authenticatedUser)
                .build();

        CartDto result = CartMapper.cartToCartDto(cart, items);
        cartService.addItemToCart(item.getId());

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post(URL.concat("/{itemId}"), item.getId()));

        response.andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void removeItem() throws Exception {

        when(userRepository.findByEmail(authenticatedUser.getEmail()))
                .thenReturn(Optional.of(authenticatedUser));

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

        when(itemRepository.findById(anyLong()))
                .thenReturn(Optional.of(item));

        Set<Long> items = new HashSet<>();
        items.add(item.getId());

        Cart cart = Cart.builder()
                .id(1L)
                .user(authenticatedUser)
                .build();

        CartDto result = CartMapper.cartToCartDto(cart, items);
        cartService.removeItemFromCart(item.getId());

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete(URL.concat("/{itemId}"), item.getId()));

        response.andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void clearCart() throws Exception {

        when(userRepository.findByEmail(authenticatedUser.getEmail()))
                .thenReturn(Optional.of(authenticatedUser));

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

        when(itemRepository.findById(anyLong()))
                .thenReturn(Optional.of(item));

        Set<Long> items = new HashSet<>();
        items.add(item.getId());

        Cart cart = Cart.builder()
                .id(1L)
                .user(authenticatedUser)
                .build();

        CartDto result = CartMapper.cartToCartDto(cart, items);
        cartService.clearCart();

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete(URL.concat("/clear")));

        response.andExpect(status().isOk());
    }
}
