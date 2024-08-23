package etu.spb.nic.online.store.cart.service;

import etu.spb.nic.online.store.cart.dto.CartDto;
import etu.spb.nic.online.store.cart.mapper.CartMapper;
import etu.spb.nic.online.store.cart.model.Cart;
import etu.spb.nic.online.store.cart.model.CartOperatorStatus;
import etu.spb.nic.online.store.cart.repository.CartRepository;
import etu.spb.nic.online.store.common.exception.AlreadyExistException;
import etu.spb.nic.online.store.common.exception.EmptyCartException;
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

        Cart cart = user.getCart();

        if (cart == null) {
            cart = createCartForUser(user);
            cart = cartRepository.save(cart);
            log.debug("Корзина создана");
        }

        log.debug("Получена корзина");
        return CartMapper.cartToCartDto(cart);
    }

    @Override
    @Transactional
    public void addItemToCart(Long itemId, Integer quantity) {
        User user = userService.getAuthenticatedUser();
        Cart cart = user.getCart();
        if (cart == null) {
            cart = createCartForUser(user);
        }
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IdNotFoundException("Товар не найден"));
        if (item.getTotalCount() < quantity) {
            throw new LassThenZeroException("Нельзя добавить товара больше, чем его есть");
        }
        if (cart.getItems().containsKey(item)) {
            throw new AlreadyExistException("Данный товар уже добавлен в корзину, повторно его добавить нельзя!");
        }

        cart.getItems().put(item, quantity);
        setStatus(item);
        if (item.getItemStatus().equals(ItemStatus.OUT_OF_STOCK)) {
            throw new EmptyItemException("Извините товар закончился, его нельзя добавить в корзину");
        }

        log.debug("Товар с itemId = {} добавлен в корзину ", itemId);

        cart.addItem(item, quantity);
        item.setTotalCount(item.getTotalCount() - quantity);
        log.debug("Количество товара обновлено");
        itemRepository.save(item);
        log.debug("Товар с id = {} добавлен в корзину в количестве {}", itemId, quantity);
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void removeItemFromCart(Long itemId) {
        User user = userService.getAuthenticatedUser();
        Cart cart = user.getCart();
        if (cart == null) {
            throw new EmptyCartException("Вы не можете ничего удалить из корзины," +
                    " так как вы не добавили еще никакой товар");
        }
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IdNotFoundException("Товар не найден"));

        if (!cart.getItems().containsKey(item)) {
            throw new IdNotFoundException("Товар, который вы хотите удалить не находится у вас в корзине!");
        }

        item.setTotalCount(item.getTotalCount() + cart.getItems().get(item));
        cart.removeItem(item);
        log.debug("Товар удален из корзины полностью");
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void changeQuantity(Long itemId, Integer quantity, CartOperatorStatus operator) {
        User user = userService.getAuthenticatedUser();
        Cart cart = user.getCart();
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IdNotFoundException("Товар не найден"));

        switch (operator) {
            case PLUS: {
                if (!cart.getItems().containsKey(item)) {
                    throw new IdNotFoundException("Такой товар не находится в Вашей корзине, добавьте его сначала");
                }
                if (item.getTotalCount() < quantity) {
                    throw new LassThenZeroException(
                            String.format("вы не можете добавить товара в количестве %d," +
                                    " так как на складе всего осталось %d!", quantity, item.getTotalCount()));
                }
                log.debug("количество товара с id = {} было увеличено на {} единиц", itemId, quantity);
                cart.getItems().put(item, cart.getItems().get(item)/* cartDto.getItemsIds().get(itemId)*/ + quantity);
                item.setTotalCount(item.getTotalCount() - quantity);

            }
            break;
            case MINUS: {
                if (!cart.getItems().containsKey(item)) {
                    throw new IdNotFoundException("Такой товар не находится в Вашей корзине");
                }
                if (cart.getItems().get(item) < quantity) {
                    throw new LassThenZeroException("Вы не можете удалить товара больше, чем там есть." +
                            "Если вы хотите полностью удалить товар, воспользуйтесь этой функцией");
                }
                log.debug("количество товара с id = {} было уменьшено на {} единиц", itemId, quantity);
                cart.getItems().put(item, cart.getItems().get(item) - quantity);
                item.setTotalCount(item.getTotalCount() + quantity);
            }
            break;
            default:
                throw new IdNotFoundException("Неизвестная команда");
        }
        log.debug("Количество товара обновлено");
        itemRepository.save(item);
        log.debug("Коризна обновлена");
        cartRepository.save(cart);
    }

    @Transactional
    private void setStatus(Item item) {
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

    private Cart createCartForUser(User user) {
        return Cart.builder()
                .user(user)
                .items(new HashMap<>())
                .build();
    }

}
