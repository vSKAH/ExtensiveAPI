package fr.skah.skmdl.api.hooks;

/*
 *  * @Created on 2021 - 11:42
 *  * @Project SKMDL
 *  * @Author jimmy  / vSKAH#0075
 */

import fr.skah.skmdl.api.hooks.basics.LandsHook;
import fr.skah.skmdl.api.hooks.basics.VaultHook;
import fr.skah.skmdl.api.hooks.basics.WorldguardHook;
import me.angeschossen.lands.api.integration.LandsIntegration;
import me.angeschossen.lands.api.integration.LandsIntegrator;
import me.angeschossen.lands.api.land.Land;
import net.milkbowl.vault.economy.Economy;

import java.util.HashMap;

public class Hooks {

    private final HashMap<String, Hook> pluginHooks = new HashMap<>();

    public Hooks() {
        pluginHooks.put("WorldGuard", new WorldguardHook());

        Hook<Economy> vaultHook = new VaultHook();
        vaultHook.registerHook();
        pluginHooks.put(vaultHook.getHookName(), vaultHook);

        Hook<LandsIntegration> landsHook = new LandsHook();
        landsHook.registerHook();
        pluginHooks.put(landsHook.getHookName(), landsHook);

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