package etu.spb.nic.online.store.cart.controller;

import etu.spb.nic.online.store.cart.dto.CartDto;
import etu.spb.nic.online.store.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public CartDto getCart() {
        return cartService.getCartForUser();
    }

    @PostMapping("/{itemId}")
    public void addItem(@PathVariable Long itemId) {
        cartService.addItemToCart(itemId);
    }

    @DeleteMapping("/{itemId}")
    public void removeItem(@PathVariable Long itemId) {
        cartService.removeItemFromCart(itemId);
    }

    @DeleteMapping("/clear")
    public void clearCart() {
        cartService.clearCart();
    }
}
