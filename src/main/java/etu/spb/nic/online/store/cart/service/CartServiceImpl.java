package etu.spb.nic.online.store.cart.service;

import etu.spb.nic.online.store.cart.model.Cart;
import etu.spb.nic.online.store.cart.dto.CartDto;
import etu.spb.nic.online.store.cart.mapper.CartMapper;
import etu.spb.nic.online.store.cart.repository.CartRepository;
import etu.spb.nic.online.store.common.exception.EmptyCartException;
import etu.spb.nic.online.store.common.exception.EmptyItemException;
import etu.spb.nic.online.store.common.exception.IdNotFoundException;
import etu.spb.nic.online.store.common.exception.LassThenZeroException;
import etu.spb.nic.online.store.item.repository.ItemRepository;
import etu.spb.nic.online.store.item.model.Item;
import etu.spb.nic.online.store.item.model.ItemStatus;
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

        checkStatus(item);
        if (item.getItemStatus().equals("Нет в наличии")) {
            throw new EmptyItemException("Извините товар закончился, его нельзя добавить в корзину");
        }

        cart.addItem(item);
        item.setTotalCount(item.getTotalCount() - 1);
        itemRepository.save(item);
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

        item.setTotalCount(item.getTotalCount() + 1);
        cart.removeItem(item);
        cartRepository.save(cart);
    }

    @Override
    public void clearCart() {
        User user = userService.getAuthenticatedUser();
        CartDto cartDto = getCartForUser();

        if (cartDto.getItemIds().isEmpty()) {
            throw new EmptyCartException("Ваша корзина пуста, Вам нечего удалять");
        }

        Set<Item> items = new HashSet<>();

        for (Long id : cartDto.getItemIds()) {
            Item checkItem = itemRepository.getById(id);
            items.add(checkItem);
        }

        Cart cart = CartMapper.cartDtoToCart(cartDto, user, items);

        for (Item item : cart.getItems()) {
            item.setTotalCount(item.getTotalCount() + 1);
            itemRepository.save(item);
        }

        cart.getItems().clear();
        cartRepository.save(cart);

    }

    private void checkStatus(Item item) {
            if (item.getTotalCount() > 5) {
                item.setItemStatus(ItemStatus.IN_STOCK.getText());
            } else if (item.getTotalCount() > 0 && item.getTotalCount() <= 5) {
                item.setItemStatus(ItemStatus.A_LITTLE.getText());
            } else if (item.getTotalCount() == 0) {
                item.setItemStatus(ItemStatus.OUT_OF_STOCK.getText());
            } else {
                throw new LassThenZeroException("Извините произошла ошибка с нашей стороны, попробуйте позже");
            }
            itemRepository.save(item);
    }

}
