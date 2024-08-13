package etu.spb.nic.online.store.cart.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder(toBuilder = true)
public class CartDto {

    private Long id;
    private Long userId;
    private Map<Long, Integer> itemsIds;
}
