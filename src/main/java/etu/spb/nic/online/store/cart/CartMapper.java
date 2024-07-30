package etu.spb.nic.online.store.cart;

import etu.spb.nic.online.store.item.model.Item;
import etu.spb.nic.online.store.user.model.User;
import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class CartMapper {

    public static CartDto cartToCartDto(Cart cart, Set<Long> itemIds) {
        return CartDto.builder()
                .id(cart.getId())
                .userId(cart.getUser().getId())
                .itemIds(itemIds)
                .build();
    }


    public Cart cartDtoToCart(CartDto cartDto, User user, Set<Item> items) {

        return Cart.builder()
                .id(cartDto.getId())
                .user(user)
                .items(items)
                .build();
    }
}
