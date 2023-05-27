package fr.skoupi.extensiveapi.minecraft.hooks;

/*  Hook
 *  By: vSKAH <vskahhh@gmail.com>

 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */

import org.bukkit.Bukkit;

public interface Hook<R> {

    /**
     * Returns the name of the hook
     *
     * @return The name of the hook.
     */
    String getHookName();

    /**
     * > Returns the class path of the object
     *
     * @return The class path of the object.
     */
    String getClasz();

    /**
     * Register a hook for the specified event.
     *
     * @return A boolean value.
     */
    boolean registerHook();

    /**
     * Returns the value of the property.
     *
     * @return The return value is a reference to the object that called the method.
     */
    R get();

    /**
     * * If the class name is null, return false.
     * * If the class name is not null, try to load the class.
     * * If the class is loaded, return true.
     * * If the class is not loaded, return false
     *
     * @return A boolean value.
     */
    default boolean classExists() {
        if (getClasz() == null) return false;
        try {
            Class.forName(getClasz());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * If the plugin is enabled and the class exists, return true
     *
     * @return The boolean value of whether the plugin is enabled and the class exists.
     */
    default boolean pluginEnabled() {
        return classExists() && Bukkit.getPluginManager().isPluginEnabled(getHookName());
    }
}