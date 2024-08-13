package etu.spb.nic.online.store.cart.mapper;

import etu.spb.nic.online.store.cart.dto.CartDto;
import etu.spb.nic.online.store.cart.model.Cart;
import etu.spb.nic.online.store.item.model.Item;
import etu.spb.nic.online.store.item.repository.ItemRepository;
import etu.spb.nic.online.store.user.model.User;
import etu.spb.nic.online.store.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CartMapper {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public static CartDto cartToCartDto(Cart cart) {
        if (cart == null) {
            throw new IllegalArgumentException("Корзина не может быть null");
        }

        // Проверка на null для items
        Map<Long, Integer> itemIds = Optional.ofNullable(cart.getItems())
                .orElse(Collections.emptyMap())
                .entrySet().stream()
                .filter(entry -> entry.getKey() != null) // фильтруем null ключи
                .collect(Collectors.toMap(entry -> {
                    Long id = entry.getKey().getId();
                    if (id == null) {
                        throw new IllegalStateException("ID товара не может быть null");
                    }
                    return id;
                }, Map.Entry::getValue));

        Long userId = Optional.ofNullable(cart.getUser())
                .map(User::getId)
                .orElseThrow(() -> new IllegalStateException("Пользователь в корзине не может быть null"));

        return CartDto.builder()
                .id(cart.getId())
                .userId(userId)
                .itemsIds(itemIds)
                .build();
    }

    public Cart cartDtoToCart(CartDto cartDto) {

        Map<Item, Integer> items = cartDto.getItemsIds().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> itemRepository.getById(entry.getKey()),
                        Map.Entry::getValue
                ));
        User user = userRepository.getById(cartDto.getUserId());

        return Cart.builder()
                .id(cartDto.getId())
                .user(user)
                .items(items)
                .build();
    }
}
