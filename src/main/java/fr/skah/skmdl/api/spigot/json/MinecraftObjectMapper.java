package fr.skah.skmdl.api.spigot.json;

/*
 *  * @Created on 2021 - 23:06
 *  * @Project UtilsAPI
 *  * @Author Jimmy  / vSKAH#0075
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import fr.skah.skmdl.api.spigot.json.adapters.itemstack.ItemStackDeserializer;
import fr.skah.skmdl.api.spigot.json.adapters.itemstack.ItemStackSerializer;
import fr.skah.skmdl.api.spigot.json.adapters.location.LocationDeserializer;
import fr.skah.skmdl.api.spigot.json.adapters.location.LocationSerializer;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class MinecraftObjectMapper {

    private final SimpleModule simpleModule;
    private final ObjectMapper objectMapper;

    public MinecraftObjectMapper() {
        simpleModule = new SimpleModule();
        simpleModule.addSerializer(Location.class, new LocationSerializer());
        simpleModule.addDeserializer(Location.class, new LocationDeserializer());

        simpleModule.addSerializer(ItemStack.class, new ItemStackSerializer(ItemStack.class));
        simpleModule.addDeserializer(ItemStack.class, new ItemStackDeserializer());

        this.objectMapper = new ObjectMapper();
    }

    public SimpleModule getSimpleModule() {
        return simpleModule;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper.registerModule(getSimpleModule());
    }
}
