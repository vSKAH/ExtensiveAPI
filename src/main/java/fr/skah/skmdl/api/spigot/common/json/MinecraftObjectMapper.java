package fr.skah.skmdl.api.spigot.common.json;

/*
 *  * @Created on 2021 - 23:06
 *  * @Project UtilsAPI
 *  * @Author Jimmy  / vSKAH#0075
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import fr.skah.skmdl.api.spigot.common.json.adapters.itemstack.ItemStackDeserializer;
import fr.skah.skmdl.api.spigot.common.json.adapters.itemstack.ItemStackSerializer;
import fr.skah.skmdl.api.spigot.common.json.adapters.location.LocationDeserializer;
import fr.skah.skmdl.api.spigot.common.json.adapters.location.LocationSerializer;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class MinecraftObjectMapper {

    @Getter
    private final SimpleModule simpleModule;
    private final ObjectMapper objectMapper;

    public MinecraftObjectMapper() {
        // It's creating a new instance of the SimpleModule class.
        simpleModule = new SimpleModule();
        // It's adding a new serializer and deserializer for the Location class.
        simpleModule.addSerializer(Location.class, new LocationSerializer());
        simpleModule.addDeserializer(Location.class, new LocationDeserializer());

        // It's adding a new serializer and deserializer for the ItemStack class.
        simpleModule.addSerializer(ItemStack.class, new ItemStackSerializer(ItemStack.class));
        simpleModule.addDeserializer(ItemStack.class, new ItemStackDeserializer());

        // It's creating a new instance of the ObjectMapper class.
        this.objectMapper = new ObjectMapper();
    }


    public ObjectMapper getObjectMapper() {
        // It's registering the module to the object mapper.
        return objectMapper.registerModule(getSimpleModule());
    }
}
