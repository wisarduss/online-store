package etu.spb.nic.online.store.item.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ItemResponseDto {
    private Long id;
    private String title;
    private String description;
    private String photoURL;
    private Long price;
    private String status;
}
