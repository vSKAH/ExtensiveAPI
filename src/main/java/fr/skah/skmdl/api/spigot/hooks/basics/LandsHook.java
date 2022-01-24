package fr.skah.skmdl.api.spigot.hooks.basics;

/*
 *  * @Created on 2021 - 11:58
 *  * @Project SKMDL
 *  * @Author jimmy  / vSKAH#0075
 */

import fr.skah.skmdl.api.spigot.ModulesPlugin;
import fr.skah.skmdl.api.spigot.hooks.Hook;
import me.angeschossen.lands.api.integration.LandsIntegration;

public class LandsHook implements Hook<LandsIntegration> {

    private LandsIntegration lands;

    @Override
    public void registerHook() {
        this.lands = new LandsIntegration(ModulesPlugin.getInstance());
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
