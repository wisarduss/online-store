package etu.spb.nic.online.store.cart.service;

import etu.spb.nic.online.store.cart.dto.CartDto;

public interface CartService {

    CartDto getCartForUser();

    void addItemToCart(Long itemId);

    void removeItemFromCart(Long itemId);

    void clearCart();
}
