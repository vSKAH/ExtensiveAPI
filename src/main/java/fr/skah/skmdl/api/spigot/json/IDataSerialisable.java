package fr.skah.skmdl.api.spigot.json;

/*
 *  * @Created on 2021 - 13:13
 *  * @Project UtilsAPI
 *  * @Author Jimmy
 */

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;

public abstract class IDataSerialisable<T> {

    private final MinecraftObjectMapper minecraftObjectMapper;

    public IDataSerialisable() {
        this.minecraftObjectMapper = new MinecraftObjectMapper();
    }

    public T load(File file, Class<T> tClass) {
        try {
            return getMinecraftObjectMapper().getObjectMapper().readValue(file, tClass);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MinecraftObjectMapper getMinecraftObjectMapper() {
        return minecraftObjectMapper;
    }
}