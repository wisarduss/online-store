package etu.spb.nic.online.store.cart;

public interface CartService {

    CartDto getCartForUser();

    void addItemToCart(Long itemId);

    void removeItemFromCart(Long itemId);

    void clearCart();
}
