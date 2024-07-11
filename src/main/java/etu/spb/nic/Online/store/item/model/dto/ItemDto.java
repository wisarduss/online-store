package etu.spb.nic.Online.store.item.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ItemDto {

    private Long id;
    private String title;
    private String description;
    private String photoURL;
    private Long price;
    private String status;
    private Long count;
}
