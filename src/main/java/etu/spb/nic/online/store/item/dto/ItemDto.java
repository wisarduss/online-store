package etu.spb.nic.online.store.item.dto;

import etu.spb.nic.online.store.item.model.ItemStatus;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;


@Data
@Builder(toBuilder = true)
public class ItemDto {

    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String photoURL;
    @NotNull
    private BigDecimal price;
    private ItemStatus status;
    @NotNull
    private Long totalCount;
    @NotEmpty
    private Set<Long> catIds;
}
