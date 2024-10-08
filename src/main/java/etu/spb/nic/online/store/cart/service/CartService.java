package etu.spb.nic.online.store.cart.service;

import etu.spb.nic.online.store.cart.dto.CartDto;
import etu.spb.nic.online.store.cart.model.CartOperatorStatus;

public interface CartService {

    CartDto getCartForUser();

    void addItemToCart(Long itemId, Integer quantity);

    void removeItemFromCart(Long itemId);

    void changeQuantity(Long itemId, Integer quantity, CartOperatorStatus operator);

}
