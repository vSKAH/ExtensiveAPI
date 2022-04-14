package fr.skah.skmdl.api.spigot.json.adapters.itemstack;

/*
 *  * @Created on 2021 - 22:06
 *  * @Project UtilsAPI
 *  * @Author Jimmy  / vSKAH#0075
 */

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ItemStackSerializer extends StdSerializer<ItemStack> {

    public ItemStackSerializer() {
        this(null);
    }

    public ItemStackSerializer(Class<ItemStack> t) {
        super(t);
    }

    @Override
    public void serialize(ItemStack itemStack, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("type", itemStack.getType().name());
        jsonGenerator.writeNumberField("amount", itemStack.getAmount());
        if(itemStack.hasItemMeta()) {
            Map<String, Object> map = new HashMap<>(itemStack.getItemMeta().serialize());
            map.remove("lore");
            map.put("lore", itemStack.getItemMeta().getLore());
            jsonGenerator.writeObjectField("meta", map);
        }
        jsonGenerator.writeEndObject();

    }
}
