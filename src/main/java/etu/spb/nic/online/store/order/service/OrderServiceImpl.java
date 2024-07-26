package etu.spb.nic.online.store.order.service;

import etu.spb.nic.online.store.common.exception.IdNotFoundException;
import etu.spb.nic.online.store.common.exception.LassThenZeroException;
import etu.spb.nic.online.store.common.exception.NotOwnerException;
import etu.spb.nic.online.store.item.ItemRepository;
import etu.spb.nic.online.store.item.model.Item;
import etu.spb.nic.online.store.item.model.ItemStatus;
import etu.spb.nic.online.store.order.dto.OrderDto;
import etu.spb.nic.online.store.order.mapper.OrderMapper;
import etu.spb.nic.online.store.order.model.Order;
import etu.spb.nic.online.store.order.repository.OrderRepository;
import etu.spb.nic.online.store.user.model.User;
import etu.spb.nic.online.store.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    public List<OrderDto> getAll() {
        return orderRepository.findAll().stream()
                .map(OrderMapper::orderToOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto addToCart(OrderDto orderDto) {
        User user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new IdNotFoundException("Пользователя с id = "
                        + orderDto.getUserId() + " не существует"));

        List<Item> items = new ArrayList<>();

        for (Long itemId : orderDto.getItemIds()) {
            Item item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new IdNotFoundException("Вещи с id = " + itemId + " не существует"));

            checkStatus(item);

            if (!item.getItemStatus().equals("Нет в наличии")) {
                item.setTotalCount(item.getTotalCount() - 1);
                items.add(item);
            } else {
                throw new LassThenZeroException("Товар закончился!");
            }
        }

        Order order = Order.builder()
                .id(orderDto.getId())
                .user(user)
                .items(items)
                .build();

        orderRepository.save(order);
        itemRepository.saveAll(items);

        return OrderMapper.orderToOrderDto(order);
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(OrderMapper::orderToOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserOrder(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IdNotFoundException("Заказа с id = " + orderId + " не существует"));

        userRepository.findById(userId)
                .orElseThrow(() -> new IdNotFoundException("Пользователь с id = " + userId + " не найден"));

        if (!order.getUser().getId().equals(userId)) {
            throw new NotOwnerException("Только владелец заказа может его удалить");
        }

        OrderDto orderDto = OrderMapper.orderToOrderDto(order);

        for (Long itemId : orderDto.getItemIds()) {
            Item item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new IdNotFoundException("Вещи с id = " + itemId + " не существует"));

            checkStatus(item);
            item.setTotalCount(item.getTotalCount() + 1);
            itemRepository.save(item); // Сохраняем изменения в базе данных
        }

        // Удаляем заказ
        orderRepository.delete(order);
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
