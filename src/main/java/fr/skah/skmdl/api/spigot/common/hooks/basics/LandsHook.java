package fr.skah.skmdl.api.spigot.common.hooks.basics;

/*
 *  * @Created on 2021 - 11:58
 *  * @Project SKMDL
 *  * @Author jimmy  / vSKAH#0075
 */

import fr.skah.skmdl.api.spigot.ModulesPlugin;
import fr.skah.skmdl.api.spigot.common.hooks.Hook;
import me.angeschossen.lands.api.integration.LandsIntegration;

public class LandsHook implements Hook<LandsIntegration> {

    private LandsIntegration lands;

    @Override
    public boolean registerHook() {
        if(!pluginEnabled()) return false;
        this.lands = new LandsIntegration(ModulesPlugin.getInstance());
        return true;
    }

    @Override
    public String getHookName() {
        return "Lands";
    }

    @Override
    public String getClasz() {
        return "me.angeschossen.lands.api.integration.LandsIntegration";
    }

    @Override
    public LandsIntegration get() {
        return lands;
    }
}
