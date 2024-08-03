package etu.spb.nic.online.store.item.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;


@Data
@Builder(toBuilder = true)
public class ItemDto {

    private Long id;
    private String title;
    private String description;
    private String photoURL;
    private Long price;
    private String status;
    private Long totalCount;
    private Set<Long> catIds;
}
