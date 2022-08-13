package fr.skah.skmdl.api.spigot.common.hooks;

/*
 *  * @Created on 2021 - 11:42
 *  * @Project SKMDL
 *  * @Author jimmy  / vSKAH#0075
 */

import fr.skah.skmdl.api.spigot.common.hooks.basics.JobsHook;
import fr.skah.skmdl.api.spigot.common.hooks.basics.LandsHook;
import fr.skah.skmdl.api.spigot.common.hooks.basics.VaultHook;
import fr.skah.skmdl.api.spigot.common.hooks.basics.WorldguardHook;
import lombok.Getter;

import java.util.HashMap;

public class Hooks {

    @Getter
    private final HashMap<String, Hook> loaded = new HashMap<>();

    public Hooks() {
        hookDefaultsPlugins();
    }

    /**
     * Hook the default plugins.
     */
    private void hookDefaultsPlugins() {
        hookPlugin(new WorldguardHook());
        hookPlugin(new VaultHook());
        hookPlugin(new LandsHook());
        hookPlugin(new JobsHook());
    }

    /**
     * If the hook is registered, add it to the pluginHooks map
     *
     * @param hook The hook to register.
     */
    public void hookPlugin(Hook hook) {
        if (hook.registerHook()) loaded.put(hook.getHookName(), hook);
    }


    /**
     * If the hook is not null, and the plugin is enabled, and the class exists, then return true
     *
     * @param name The name of the plugin you want to check.
     * @return A boolean value.
     */
    public boolean isHooked(String name) {
        try {
            Hook hook = loaded.get(name);
            return hook.getClasz() == null ? hook.pluginEnabled() : hook.pluginEnabled() && hook.getClasz() != null && hook.classExists();
        } catch (Exception e) {
            return false;
        }

    }
}