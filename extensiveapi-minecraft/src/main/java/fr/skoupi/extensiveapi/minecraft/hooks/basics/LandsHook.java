package fr.skoupi.extensiveapi.minecraft.hooks.basics;

/*  LandsHook
 *  By: vSKAH <vskahhh@gmail.com>

 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */

import fr.skoupi.extensiveapi.minecraft.ModulesPlugin;
import fr.skoupi.extensiveapi.minecraft.hooks.Hook;
import me.angeschossen.lands.api.integration.LandsIntegration;

/**
 * This class is a hook for the Lands plugin.
 */
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
