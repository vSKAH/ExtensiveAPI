package fr.skah.skmdl.api.spigot.json;

/*
 *  * @Created on 2021 - 13:13
 *  * @Project UtilsAPI
 *  * @Author Jimmy
 */

import com.fasterxml.jackson.core.type.TypeReference;
import fr.skah.skmdl.api.spigot.ModulesPlugin;

import java.io.File;
import java.io.IOException;

public abstract class IDataSerialisable<T> {

    private final MinecraftObjectMapper minecraftObjectMapper;

    public IDataSerialisable() {
        this.minecraftObjectMapper = new MinecraftObjectMapper();
    }

    public T load(File file, Class<T> tClass) {
        try {
            T type = getMinecraftObjectMapper().getObjectMapper().readValue(file, tClass);
            if(ModulesPlugin.getInstance() != null && ModulesPlugin.getInstance().isEnabled()) {
                ModulesPlugin.getInstance().getLogger().info("File ".concat(file.getAbsolutePath()).concat(" has been loaded"));
            }
            return type;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public T convertValue(Object object, TypeReference<T> typeReference) {
        return getMinecraftObjectMapper().getObjectMapper().convertValue(object, typeReference);
    }

    public void save(File file, Object object) {
        try {
            getMinecraftObjectMapper().getObjectMapper().writeValue(file, object);
            if(ModulesPlugin.getInstance() != null && ModulesPlugin.getInstance().isEnabled()) {
                ModulesPlugin.getInstance().getLogger().info("File ".concat(file.getAbsolutePath()).concat(" has been saved"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MinecraftObjectMapper getMinecraftObjectMapper() {
        return minecraftObjectMapper;
    }
}