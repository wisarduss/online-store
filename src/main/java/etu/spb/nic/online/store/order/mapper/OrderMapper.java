package etu.spb.nic.online.store.order.mapper;

import etu.spb.nic.online.store.item.model.Item;
import etu.spb.nic.online.store.order.dto.OrderDto;
import etu.spb.nic.online.store.order.model.Order;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class OrderMapper {

    public static OrderDto orderToOrderDto(Order order) {
        List<Long> itemIds = order.getItems().stream()
                .map(Item::getId) // Получаем ID каждого Item
                .collect(Collectors.toList()); // Собираем в список

        return OrderDto.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .itemIds(itemIds) // Передаем список ID
                .build();
    }

}
