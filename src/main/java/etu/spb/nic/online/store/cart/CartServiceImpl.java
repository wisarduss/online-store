package etu.spb.nic.online.store.cart;

import etu.spb.nic.online.store.common.exception.IdNotFoundException;
import etu.spb.nic.online.store.common.exception.LassThenZeroException;
import etu.spb.nic.online.store.item.ItemRepository;
import etu.spb.nic.online.store.item.dto.ItemDto;
import etu.spb.nic.online.store.item.mapper.ItemMapper;
import etu.spb.nic.online.store.item.model.Item;
import etu.spb.nic.online.store.item.model.ItemStatus;
import etu.spb.nic.online.store.user.model.User;
import etu.spb.nic.online.store.user.repository.UserRepository;
import etu.spb.nic.online.store.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final UserService userService;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;


    @Override
    public List<Item> getItemsInCart() {
        User user = userService.getAuthenticatedUser();
        return user.getItems();
    }

    @Override
    public void addItemsToCart(Long itemId) {
        User user = userService.getAuthenticatedUser();
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IdNotFoundException("Товар с id = " + itemId + " не найден"));

        checkStatus(item);

        if (!item.getItemStatus().equals("Нет в наличии")) {
            item.setTotalCount(item.getTotalCount() - 1);
            itemRepository.save(item);
            user.getItems().add(item);
            System.out.println(user.getItems().get(0));
        } else {
            throw new LassThenZeroException("Товар закончился!");
        }
    }

    @Override
    public void removeItemsFromCart(Long itemId) {
        User user = userService.getAuthenticatedUser();
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IdNotFoundException("Товар с id = " + itemId + " не найден"));

        user.getItems().remove(item);
        item.setTotalCount(item.getTotalCount() + 1);
    }

    private Item checkStatus(Item item) {
        if (item.getTotalCount() > 5) {
            item.setItemStatus(ItemStatus.IN_STOCK.getText());
        } else if (item.getTotalCount() > 0 && item.getTotalCount() <= 5) {
            item.setItemStatus(ItemStatus.A_LITTLE.getText());
        } else if (item.getTotalCount() == 0) {
            item.setItemStatus(ItemStatus.OUT_OF_STOCK.getText());
        } else {
            throw new LassThenZeroException("Извините произошла ошибка с нашей стороны, попробуйте позже");
        }

        return itemRepository.save(item);
    }
}
