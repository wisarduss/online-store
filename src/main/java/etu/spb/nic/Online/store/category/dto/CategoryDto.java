package etu.spb.nic.Online.store.category.dto;

import etu.spb.nic.Online.store.item.model.Item;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class CategoryDto {

    private Long id;
    private String title;
    private List<Item> items;
}
