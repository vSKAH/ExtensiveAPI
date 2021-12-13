package fr.skah.skmdl.api.hooks.basics;

/*
 *  * @Created on 2021 - 11:53
 *  * @Project SKMDL
 *  * @Author jimmy  / vSKAH#0075
 */

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import fr.skah.skmdl.api.hooks.Hook;

public class WorldguardHook implements Hook {

    @Override
    public String getHookName() {
        return "WorldGuard";
    }

    @Override
    public String getClasz() {
        return null;
    }

    @Override
    public void registerHook() {}

    @Override
    public WorldGuardPlugin get() {
        return WorldGuardPlugin.inst();
    }
}
