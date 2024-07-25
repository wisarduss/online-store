package etu.spb.nic.online.store.order.service;

import etu.spb.nic.online.store.order.dto.OrderDto;

import java.util.List;

public interface OrderService {

    List<OrderDto> getAll();

    OrderDto addToCart(OrderDto orderDto);

    List<OrderDto> getUserOrders(Long userId);

}
