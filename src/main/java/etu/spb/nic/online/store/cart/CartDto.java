package etu.spb.nic.online.store.cart;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder(toBuilder = true)
public class CartDto {

    private Long id;

    private Long userId;

    private Set<Long> itemIds;
}
