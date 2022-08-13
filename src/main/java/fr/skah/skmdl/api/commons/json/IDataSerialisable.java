package fr.skah.skmdl.api.commons.json;

/*
 *  * @Created on 2021 - 13:13
 *  * @Project UtilsAPI
 *  * @Author Jimmy
 */

import fr.skah.skmdl.api.spigot.ModulesPlugin;
import fr.skah.skmdl.api.spigot.common.json.MinecraftObjectMapper;
import lombok.Getter;

import java.io.File;
import java.io.IOException;

public abstract class IDataSerialisable<T> {

    @Getter
    private final MinecraftObjectMapper minecraftObjectMapper;

    public IDataSerialisable() {
        this.minecraftObjectMapper = new MinecraftObjectMapper();
    }

    /**
     * It loads a file and returns the object of the class that was passed in
     *
     * @param file The file to load from
     * @param tClass The class of the object you want to load.
     * @return A MinecraftObjectMapper object
     */
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

    /**
     * It saves an object to a file
     *
     * @param file The file to save the object to.
     * @param object The object to save
     */
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

}