package etu.spb.nic.online.store.cart.controller;

import etu.spb.nic.online.store.cart.dto.CartDto;
import etu.spb.nic.online.store.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
