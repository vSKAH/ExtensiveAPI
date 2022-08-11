package fr.skah.skmdl.api.spigot.common.json.adapters.itemstack;

/*
 *  * @Created on 2021 - 22:16
 *  * @Project UtilsAPI
 *  * @Author Jimmy  / vSKAH#0075
 */

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.io.IOException;
import java.util.*;

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

            
            Color color = null;
            if(t.containsKey("color")) {
                if(jsonNode.get("meta").has("color")) {
                    JsonNode colorNode = jsonNode.get("meta").get("color");
                    color = Color.fromRGB(colorNode.get("red").intValue(), colorNode.get("green").intValue(), colorNode.get("blue").intValue());
                }
                t.remove("color");
            }



            ItemMeta meta = (ItemMeta) ConfigurationSerialization.deserializeObject(t);
            t.clear();

            if(meta instanceof LeatherArmorMeta && color != null) {
                ((LeatherArmorMeta) meta).setColor(color);
            }
            
            if (jsonNode.get("meta").has("attribute-modifiers")) {

                jsonNode.get("meta").get("attribute-modifiers").forEach(jsonNode1 -> {
                    JsonNode node = jsonNode1.get(0);
                    final Map<String, Object> noded;
                    try {
                        noded = mapper.treeToValue(node, Map.class);

                        final AttributeModifier modifier = new AttributeModifier(UUID.fromString(noded.get("uniqueId").toString()), (String) noded.get("name"), (Double) noded.get("amount"), AttributeModifier.Operation.valueOf(noded.get("operation").toString().toUpperCase()), EquipmentSlot.valueOf((String) noded.get("slot").toString()));
                        final Attribute attribute = Attribute.valueOf(modifier.getName().toUpperCase().replace(".", "_"));
                        meta.addAttributeModifier(attribute, modifier);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                });

            }
            stack.setItemMeta(meta);
        }
        return stack;
    }
}
