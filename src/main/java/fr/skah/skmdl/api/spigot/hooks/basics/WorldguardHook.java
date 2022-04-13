package fr.skah.skmdl.api.spigot.hooks.basics;

/*
 *  * @Created on 2021 - 11:53
 *  * @Project SKMDL
 *  * @Author jimmy  / vSKAH#0075
 */

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import fr.skah.skmdl.api.spigot.hooks.Hook;

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

    @Override
    public WorldGuardPlugin get() {
        return WorldGuardPlugin.inst();
    }
}
