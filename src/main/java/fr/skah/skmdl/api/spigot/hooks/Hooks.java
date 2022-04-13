package fr.skah.skmdl.api.spigot.hooks;

/*
 *  * @Created on 2021 - 11:42
 *  * @Project SKMDL
 *  * @Author jimmy  / vSKAH#0075
 */

import fr.skah.skmdl.api.spigot.hooks.basics.JobsHook;
import fr.skah.skmdl.api.spigot.hooks.basics.LandsHook;
import fr.skah.skmdl.api.spigot.hooks.basics.VaultHook;
import fr.skah.skmdl.api.spigot.hooks.basics.WorldguardHook;
import me.angeschossen.lands.api.integration.LandsIntegration;
import net.milkbowl.vault.economy.Economy;

import java.util.HashMap;

public class Hooks {

    private final HashMap<String, Hook> pluginHooks = new HashMap<>();

    public Hooks() {
        hookDefaultsPlugins();
    }

    private void hookDefaultsPlugins() {
        hookPlugin(new WorldguardHook());
        hookPlugin(new VaultHook());
        hookPlugin(new LandsHook());
        hookPlugin(new JobsHook());
    }

    public void hookPlugin(Hook hook) {
        if (hook.registerHook()) pluginHooks.put(hook.getHookName(), hook);
    }

    public HashMap<String, Hook> getLoaded() {
        return pluginHooks;
    }

    public boolean isHooked(String name) {
        try {
            Hook hook = pluginHooks.get(name);
            return hook.getClasz() == null ? hook.pluginEnabled() : hook.pluginEnabled() && hook.getClasz() != null && hook.classExists();
        } catch (Exception e) {
            return false;
        }

    }
}