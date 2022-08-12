package fr.skah.skmdl.api.spigot.common.json.adapters.itemstack;

/*
 *  * @Created on 2021 - 22:06
 *  * @Project UtilsAPI
 *  * @Author Jimmy  / vSKAH#0075
 */

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import fr.skah.skmdl.api.spigot.ModulesPlugin;
import fr.skah.skmdl.api.spigot.common.utils.MinecraftVersion;
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
        if (itemStack.hasItemMeta()) {
            Map<String, Object> map = new HashMap<>(itemStack.getItemMeta().serialize());
            if (itemStack.getItemMeta().hasLore()) {
                map.remove("lore");
                map.put("lore", itemStack.getItemMeta().getLore());
            }
            if (itemStack.getItemMeta().hasDisplayName()) {
                map.remove("display-name");
                map.put("display-name", itemStack.getItemMeta().getDisplayName());
            }
            if (MinecraftVersion.atLeast(MinecraftVersion.V.v1_9)) {
                if (itemStack.getItemMeta().getAttributeModifiers() != null && itemStack.getItemMeta().hasAttributeModifiers())
                    itemStack.getItemMeta().getAttributeModifiers().forEach((attribute, attributeModifier) -> attributeModifier.serialize());
            }
            jsonGenerator.writeObjectField("meta", map);
        }
        jsonGenerator.writeEndObject();

    }
}
