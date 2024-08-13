package etu.spb.nic.online.store.cart.service;

import etu.spb.nic.online.store.cart.dto.CartDto;
import etu.spb.nic.online.store.cart.mapper.CartMapper;
import etu.spb.nic.online.store.cart.model.Cart;
import etu.spb.nic.online.store.cart.repository.CartRepository;
import etu.spb.nic.online.store.common.exception.EmptyItemException;
import etu.spb.nic.online.store.common.exception.IdNotFoundException;
import etu.spb.nic.online.store.common.exception.LassThenZeroException;
import etu.spb.nic.online.store.item.model.Item;
import etu.spb.nic.online.store.item.model.ItemStatus;
import etu.spb.nic.online.store.item.repository.ItemRepository;
import etu.spb.nic.online.store.user.model.User;
import etu.spb.nic.online.store.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final UserService userService;
    private final CartMapper cartMapper;

    @Override
    @Transactional
    public CartDto getCartForUser() {
        User user = userService.getAuthenticatedUser();

        Cart cart = cartRepository.findByUser(user);

        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart = cartRepository.save(cart);
            log.debug("Корзина создана");
        }

        Map<Item, Integer> items = cart.getItems();
        if (items == null) {
            items = new HashMap<>();
        }

        Map<Long, Integer> itemsMap = new HashMap<>();
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            Item item = entry.getKey();
            Integer quantity = entry.getValue();
            itemsMap.put(item.getId(), itemsMap.getOrDefault(item.getId(), 0) + quantity);
        }

        log.debug("Получена корзина");
        return CartMapper.cartToCartDto(cart);
    }

    @Override
    @Transactional
    public void addItemToCart(Long itemId, Integer quantity) {
        User user = userService.getAuthenticatedUser();
        CartDto cartDto = getCartForUser();
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IdNotFoundException("Товар не найден"));
        if (item.getTotalCount() < quantity) {
            throw new LassThenZeroException("Нельзя добавить товара больше, чем его есть");
        }
        Map<Item, Integer> items = new HashMap<>();

        for (Long id : cartDto.getItemsIds().keySet()) {
            Item checkItem = itemRepository.getById(id);
            items.put(checkItem, quantity);
        }

        Cart cart = cartMapper.cartDtoToCart(cartDto);

        checkStatus(item);
        if (item.getItemStatus().equals(ItemStatus.OUT_OF_STOCK)) {
            throw new EmptyItemException("Извините товар закончился, его нельзя добавить в корзину");
        }

        log.debug("Товар с itemId = {} добавлен в корзину ", itemId);

        cart.addItem(item, quantity);
        item.setTotalCount(item.getTotalCount() - quantity);
        itemRepository.save(item);
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void removeItemFromCart(Long itemId) {
        User user = userService.getAuthenticatedUser();
        CartDto cartDto = getCartForUser();
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IdNotFoundException("Товар не найден"));
        Map<Item, Integer> items = new HashMap<>();

        for (Long id : cartDto.getItemsIds().keySet()) {
            Item checkItem = itemRepository.getById(id);
            items.put(checkItem, cartDto.getItemsIds().get(id));
        }

        Cart cart = cartMapper.cartDtoToCart(cartDto);

        if (!items.containsKey(item)) {
            throw new IdNotFoundException("Товар, который вы хотите удалить не находится у вас в корзине!");
        }

        item.setTotalCount(item.getTotalCount() + cart.getItems().get(item));
        cart.removeItem(item);
        log.debug("Товар удален из корзины полностью");
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void changeQuantity(Long itemId, Integer quantity, String operator) {
        User user = userService.getAuthenticatedUser();
        CartDto cartDto = getCartForUser();

        switch (operator) {
            case "plus": {
                if (!cartDto.getItemsIds().containsKey(itemId)) {
                    throw new IdNotFoundException("Такой товар не находится в Вашей корзине, добавьте его сначала");
                }
                cartDto.getItemsIds().put(itemId, cartDto.getItemsIds().get(itemId) + quantity);
            }
            break;
            case "minus": {
                if (!cartDto.getItemsIds().containsKey(itemId)) {
                    throw new IdNotFoundException("Такой товар не находится в Вашей корзине");
                }
                if (cartDto.getItemsIds().get(itemId) < quantity) {
                    throw new LassThenZeroException("Вы не можете удалить товара больше, чем там есть." +
                            "Если вы хотите полностью удалить товар, воспользуйтесь этой функцией");
                }

                cartDto.getItemsIds().put(itemId, cartDto.getItemsIds().get(itemId) - quantity);
            }
            break;
            default:
                throw new IdNotFoundException("Неизвестная команда");
        }
        Cart cart = cartMapper.cartDtoToCart(cartDto);
        cartRepository.save(cart);
    }

    @Transactional
    private void checkStatus(Item item) {
        if (item.getTotalCount() > 5) {
            item.setItemStatus(ItemStatus.IN_STOCK);
        } else if (item.getTotalCount() > 0 && item.getTotalCount() <= 5) {
            item.setItemStatus(ItemStatus.A_LITTLE);
        } else if (item.getTotalCount() == 0) {
            item.setItemStatus(ItemStatus.OUT_OF_STOCK);
        } else {
            throw new LassThenZeroException("Извините произошла ошибка с нашей стороны, попробуйте позже");
        }
        log.debug("Статус товара обновлен");
        itemRepository.save(item);
    }

}
