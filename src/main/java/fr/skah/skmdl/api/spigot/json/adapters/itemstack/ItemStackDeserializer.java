package fr.skah.skmdl.api.spigot.json.adapters.itemstack;

/*
 *  * @Created on 2021 - 22:16
 *  * @Project UtilsAPI
 *  * @Author Jimmy  / vSKAH#0075
 */

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class ItemStackDeserializer extends JsonDeserializer<ItemStack> {

    @Override
    public ItemStack deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        final ObjectCodec objectCodec = jsonParser.getCodec();
        final JsonNode jsonNode = objectCodec.readTree(jsonParser);
        final ObjectMapper mapper = new ObjectMapper();
        final ItemStack stack = new ItemStack(Objects.requireNonNull(Material.getMaterial(jsonNode.get("type").asText())), jsonNode.get("amount").asInt());

        if (jsonNode.has("meta")) {
            final Map<String, Object> t = mapper.treeToValue(jsonNode.get("meta"), Map.class);
            t.put("==", "ItemMeta");
            ItemMeta meta = (ItemMeta) ConfigurationSerialization.deserializeObject(t);
            t.clear();
            stack.setItemMeta(meta);
        }
        return stack;
    }
}
