package etu.spb.nic.online.store.cart;

import etu.spb.nic.online.store.item.dto.ItemDto;
import etu.spb.nic.online.store.item.model.Item;

import java.util.List;

public interface CartService {

    List<Item> getItemsInCart();

    void addItemsToCart(Long itemId);

    void removeItemsFromCart(Long itemId);
}
