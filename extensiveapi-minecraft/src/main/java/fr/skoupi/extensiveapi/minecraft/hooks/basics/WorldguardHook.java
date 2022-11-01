package fr.skoupi.extensiveapi.minecraft.hooks.basics;

/*  WorldguardHook
 *  By: vSKAH <vskahhh@gmail.com>

 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import fr.skoupi.extensiveapi.minecraft.hooks.Hook;

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
