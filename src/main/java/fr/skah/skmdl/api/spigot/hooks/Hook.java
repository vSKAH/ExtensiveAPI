package fr.skah.skmdl.api.spigot.hooks;

/*
 *  * @Created on 2021 - 11:41
 *  * @Project SKMDL
 *  * @Author jimmy  / vSKAH#0075
 */

import org.bukkit.Bukkit;

public interface Hook<R> {

    String getHookName();

    String getClasz();

    void registerHook();

    R get();

    default boolean classExists() {
        if (getClasz() == null) return false;
        try {
            Class.forName(getClasz());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    default boolean pluginEnabled() {
        return Bukkit.getPluginManager().isPluginEnabled(getHookName()) && classExists();
    }
}