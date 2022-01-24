package fr.skah.skmdl.api.spigot.modules.manage;

/*
 *  * @Created on 2021 - 17:22
 *  * @Project SKMDL
 *  * @Author jimmy  / vSKAH#0075
 */

import com.google.common.collect.Maps;
import fr.skah.skmdl.api.spigot.modules.enums.ModuleState;
import fr.skah.skmdl.api.spigot.modules.models.Module;
import fr.skah.skmdl.api.spigot.modules.models.ModuleOption;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.ConcurrentMap;

public class ModuleManager {

    private static final ConcurrentMap<String, Module> modules = Maps.newConcurrentMap();


    public static void registerModule(Module module) {
        module.onStartup();
        modules.put(module.getModuleOptions().getModuleName(), module);
        loadModule(module.getModuleOptions().getModuleName());
    }

    public static void loadModule(String moduleName) {
        Module module = modules.get(moduleName);
            for (String pluginDependency : module.getModuleOptions().getPluginDependencies()) {
                Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginDependency);
                if (plugin == null || !plugin.isEnabled()) {
                    module.getLogger().warn("Impossible de charger le module " + moduleName + ". Il manque " + pluginDependency);
                    return;
                }
            }
        modules.get(moduleName).onEnable();
    }

    public static void unloadModule(String moduleName) {
        modules.get(moduleName).onDisable();
    }

    public static Module changeModuleState(String moduleName) {
        Module module = ModuleManager.getModules().get(moduleName);
        ModuleOption moduleOption = module.getModuleOptions();
        if (!moduleOption.isCanBeDisabled()) return module;
        if (module.getModuleState() == ModuleState.ENABLED) ModuleManager.unloadModule(moduleName);
        else ModuleManager.loadModule(moduleOption.getModuleName());
        return module;
    }

    public static ConcurrentMap<String, Module> getModules() {
        return modules;
    }
}
