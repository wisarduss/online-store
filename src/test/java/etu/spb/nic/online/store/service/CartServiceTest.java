/*
package etu.spb.nic.online.store.service;

import etu.spb.nic.online.store.authentication.config.JWTFilter;
import etu.spb.nic.online.store.authentication.controller.AuthController;
import etu.spb.nic.online.store.authentication.security.PersonDetails;
import etu.spb.nic.online.store.authentication.service.AuthenticationService;
import etu.spb.nic.online.store.cart.dto.CartDto;
import etu.spb.nic.online.store.cart.model.Cart;
import etu.spb.nic.online.store.cart.repository.CartRepository;
import etu.spb.nic.online.store.cart.service.CartService;
import etu.spb.nic.online.store.category.model.Category;
import etu.spb.nic.online.store.common.util.JWTUtil;
import etu.spb.nic.online.store.item.model.Item;
import etu.spb.nic.online.store.item.model.ItemStatus;
import etu.spb.nic.online.store.item.repository.ItemRepository;
import etu.spb.nic.online.store.user.model.User;
import etu.spb.nic.online.store.user.repository.UserRepository;
import etu.spb.nic.online.store.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@Profile("test")
public class CartServiceTest {

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JWTFilter jwtFilter;

    @MockBean
    private JWTUtil jwtUtil;

    @MockBean
    private AuthController authController;

    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    private User authenticatedUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

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
    @WithMockUser(username = "test@example.com")
    void getCart() {
        when(userRepository.findByEmail(authenticatedUser.getEmail()))
                .thenReturn(Optional.of(authenticatedUser));

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
                .price(BigDecimal.valueOf(89999L))
                .totalCount(345L)
                .itemStatus(ItemStatus.IN_STOCK)
                .categories(categories)
                .build();

        Set<Item> items = new HashSet<>();
        items.add(item);

        Cart cart = Cart.builder()
                .id(1L)
                .user(authenticatedUser)
                .items(items)
                .build();

        when(cartRepository.findByUser(any()))
                .thenReturn(cart);

        CartDto cartDto = cartService.getCartForUser();

        assertThat(cartDto).isNotNull();
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void addItemToCart() {
        when(userRepository.findByEmail(authenticatedUser.getEmail()))
                .thenReturn(Optional.of(authenticatedUser));

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
                .price(BigDecimal.valueOf(89999L))
                .totalCount(345L)
                .itemStatus(ItemStatus.IN_STOCK)
                .categories(categories)
                .build();

        Item secondItem = Item.builder()
                .id(2L)
                .title("iphone 15 pro")
                .description("мощный телефон")
                .photoURL("photo_url")
                .price(BigDecimal.valueOf(89999L))
                .totalCount(345L)
                .itemStatus(ItemStatus.IN_STOCK)
                .categories(categories)
                .build();

        Set<Item> items = new HashSet<>();
        items.add(item);

        Cart cart = Cart.builder()
                .id(1L)
                .user(authenticatedUser)
                .items(items)
                .build();

        when(itemRepository.getById(anyLong()))
                .thenReturn(item);
        when(itemRepository.findById(anyLong()))
                .thenReturn(Optional.of(secondItem));
        when(cartRepository.save(any()))
                .thenReturn(cart);

        cartService.addItemToCart(secondItem.getId());

        verify(itemRepository, times(2)).getById(anyLong());
        verify(itemRepository, times(1)).findById(anyLong());
        verify(cartRepository, times(2)).save(any());

    }

}
*/
