package etu.spb.nic.online.store.order.dto;

import etu.spb.nic.online.store.item.model.Item;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class OrderDto {

    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private List<Long> itemIds;

}
