package etu.spb.nic.online.store.order.service;

import etu.spb.nic.online.store.common.exception.IdNotFoundException;
import etu.spb.nic.online.store.common.exception.LassThenZeroException;
import etu.spb.nic.online.store.item.ItemRepository;
import etu.spb.nic.online.store.item.model.Item;
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

            if (item.getTotalCount() > 0) {
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


}
