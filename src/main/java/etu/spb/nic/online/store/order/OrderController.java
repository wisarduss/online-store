package etu.spb.nic.online.store.order;

import etu.spb.nic.online.store.order.dto.OrderDto;
import etu.spb.nic.online.store.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getAll() {
        return orderService.getAll();
    }

    @PostMapping
    public OrderDto addToCart(@RequestBody OrderDto orderDto) {
        return orderService.addToCart(orderDto);
    }

    @GetMapping("/{userId}")
    public List<OrderDto> getUserOrders(@PathVariable Long userId) {
        return orderService.getUserOrders(userId);
    }

}
