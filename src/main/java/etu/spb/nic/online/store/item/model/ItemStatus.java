package etu.spb.nic.online.store.item.model;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ItemStatus {
    OUT_OF_STOCK("Нет в наличии"),
    IN_STOCK("В наличии"),
    A_LITTLE("Мало");

    private String text;



}
