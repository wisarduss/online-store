package etu.spb.nic.online.store.order.service;

import etu.spb.nic.online.store.exception.IdNotFoundException;
import etu.spb.nic.online.store.exception.LassThenZeroException;
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
        List<Item> items = new ArrayList<>();
        Item item = itemRepository.findById(orderDto.getItemIds().get(0))
                .orElseThrow(() -> new RuntimeException("Item not found"));
        if (item.getTotalCount() <= 0) {
            throw new RuntimeException("Item is out of stock");
        }

        User user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new IdNotFoundException("Пользователь с id = "
                        + orderDto.getUserId() + " не найден"));

        items.add(item);

        Order order = Order.builder()
                .id(orderDto.getId())
                .user(user)
                .items(items)
                .build();

        item.setTotalCount(item.getTotalCount() - 1);
        itemRepository.save(item);

        return OrderMapper.orderToOrderDto(orderRepository.save(order));
    }
}
