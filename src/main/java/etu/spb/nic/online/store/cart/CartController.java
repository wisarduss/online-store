package etu.spb.nic.online.store.cart;

import etu.spb.nic.online.store.item.dto.ItemDto;
import etu.spb.nic.online.store.item.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public List<Item> getCart() {
        return cartService.getItemsInCart();
    }

    @PostMapping("/add/{itemId}")
    public void addPerfumeToCart(@PathVariable Long itemId) {
        cartService.addItemsToCart(itemId);
    }

    @PostMapping("/remove/{itemId}")
    public void removePerfumeFromCart(@PathVariable Long itemId) {
        cartService.removeItemsFromCart(itemId);
    }
}
