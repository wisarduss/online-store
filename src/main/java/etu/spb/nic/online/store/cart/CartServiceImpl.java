package etu.spb.nic.online.store.cart;

import etu.spb.nic.online.store.common.exception.IdNotFoundException;
import etu.spb.nic.online.store.item.ItemRepository;
import etu.spb.nic.online.store.item.model.Item;
import etu.spb.nic.online.store.user.model.User;
import etu.spb.nic.online.store.user.repository.UserRepository;
import etu.spb.nic.online.store.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public CartDto getCartForUser() {
        User user = userService.getAuthenticatedUser();

        Cart cart = cartRepository.findByUser(user);


        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart = cartRepository.save(cart);
        }

        Set<Long> itemIds = new HashSet<>();
        for (Item item : cart.getItems()) {
            itemIds.add(item.getId());
        }

        return CartDto.builder()
                .id(cart.getId())
                .userId(user.getId())
                .itemIds(itemIds)
                .build();
    }

    @Override
    public void addItemToCart(Long itemId) {
        User user = userService.getAuthenticatedUser();
        CartDto cartDto = getCartForUser();
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IdNotFoundException("Товар не найден"));
        Set<Item> items = new HashSet<>();

        for (Long id : cartDto.getItemIds()) {
            Item checkItem = itemRepository.getById(id);
            items.add(checkItem);
        }
        Cart cart = CartMapper.cartDtoToCart(cartDto, user, items);

        cart.addItem(item);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long itemId) {
        User user = userService.getAuthenticatedUser();
        CartDto cartDto = getCartForUser();
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IdNotFoundException("Товар не найден"));
        Set<Item> items = new HashSet<>();

        for (Long id : cartDto.getItemIds()) {
            Item checkItem = itemRepository.getById(id);
            items.add(checkItem);
        }

        Cart cart = CartMapper.cartDtoToCart(cartDto, user, items);

        if (!items.contains(item)) {
            throw new IdNotFoundException("Товар, который вы хотите удалить не находится у вас в корзине!");
        }

        cart.removeItem(item);
        cartRepository.save(cart);
    }

    @Override
    public void clearCart() {
        User user = userService.getAuthenticatedUser();
        CartDto cartDto = getCartForUser();

        Set<Item> items = new HashSet<>();

        for (Long id : cartDto.getItemIds()) {
            Item checkItem = itemRepository.getById(id);
            items.add(checkItem);
        }

        Cart cart = CartMapper.cartDtoToCart(cartDto, user, items);

        cart.getItems().clear();
        cartRepository.save(cart);

    }

}
