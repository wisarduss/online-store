package etu.spb.nic.online.store.cart.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Data
@Builder(toBuilder = true)
public class CartDto {

    private Long id;
    @NotBlank
    private Long userId;
    @NotEmpty
    private Map<Long, Integer> itemsIds;
}
