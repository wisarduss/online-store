package etu.spb.nic.online.store.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class OrderDto {

    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private List<Long> itemIds;

}
