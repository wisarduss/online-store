package etu.spb.nic.online.store.item.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ItemStatus {
    OUT_OF_STOCK("Нет в наличии"),
    IN_STOCK("В наличии"),
    A_LITTLE("Мало");

    private String text;


}
