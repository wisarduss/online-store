package etu.spb.nic.online.store.item.dto;

import etu.spb.nic.online.store.item.model.ItemStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
public class ItemResponseDto {

    private Long id;
    private String title;
    private String description;
    private String photoURL;
    private BigDecimal price;
    private ItemStatus status;
}
