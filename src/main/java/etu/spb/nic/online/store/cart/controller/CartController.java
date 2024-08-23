package etu.spb.nic.online.store.cart.controller;

import etu.spb.nic.online.store.cart.dto.CartDto;
import etu.spb.nic.online.store.cart.model.CartOperatorStatus;
import etu.spb.nic.online.store.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping("/{itemId}/{quantity}")
    public void addItem(@PathVariable Long itemId, @PathVariable Integer quantity) {
        cartService.addItemToCart(itemId, quantity);
    }

    @DeleteMapping("/{itemId}")
    public void removeItem(@PathVariable Long itemId) {
        cartService.removeItemFromCart(itemId);
    }

    @PatchMapping("/{itemId}/{quantity}")
    public void changeQuantity(@PathVariable Long itemId,
                               @PathVariable Integer quantity,
                               @RequestParam(name = "operator") CartOperatorStatus operator) {
        cartService.changeQuantity(itemId, quantity, operator);
    }
}
