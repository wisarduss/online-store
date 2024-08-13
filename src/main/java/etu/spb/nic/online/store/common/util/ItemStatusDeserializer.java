package etu.spb.nic.online.store.common.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import etu.spb.nic.online.store.item.model.ItemStatus;

import java.io.IOException;

public class ItemStatusDeserializer extends JsonDeserializer<ItemStatus> {

    @Override
    public ItemStatus deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String value = jp.getText();
        switch (value) {
            case "В наличии":
                return ItemStatus.IN_STOCK; // Соответствующее значение enum
            case "На складе":
                return ItemStatus.A_LITTLE;
            case "Нет в наличии":
                return ItemStatus.OUT_OF_STOCK;
            default:
                throw new IOException("Unknown status: " + value);
        }
    }
}
