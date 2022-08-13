package fr.skah.skmdl.api.spigot.common.hooks.basics;

/*
 *  * @Created on 2021 - 11:53
 *  * @Project SKMDL
 *  * @Author jimmy  / vSKAH#0075
 */

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import fr.skah.skmdl.api.spigot.common.hooks.Hook;

/**
 * This class is a hook for WorldGuard.
 */
public class WorldguardHook implements Hook<WorldGuardPlugin> {

    @Override
    public String getHookName() {
        return "WorldGuard";
    }

    @Override
    public String getClasz() {
        return "com.sk89q.worldguard.bukkit.WorldGuardPlugin";
    }

    @Override
    public boolean registerHook() {
        return pluginEnabled();
    }

    /**
     * This function returns the WorldGuardPlugin instance.
     *
     * @return The WorldGuardPlugin instance.
     */
    @Override
    public WorldGuardPlugin get() {
        return WorldGuardPlugin.inst();
    }
}
