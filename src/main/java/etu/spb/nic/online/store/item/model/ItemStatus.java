package etu.spb.nic.online.store.item.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ItemStatus {
    OUT_OF_STOCK,
    IN_STOCK,
    A_LITTLE;

    private String text;


}
