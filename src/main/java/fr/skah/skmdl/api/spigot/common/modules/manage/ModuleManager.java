package fr.skah.skmdl.api.spigot.common.modules.manage;

/*
 *  * @Created on 2021 - 17:22
 *  * @Project SKMDL
 *  * @Author jimmy  / vSKAH#0075
 */

import com.google.common.collect.Maps;
import fr.skah.skmdl.api.spigot.ModulesPlugin;
import fr.skah.skmdl.api.spigot.common.modules.enums.ModuleState;
import fr.skah.skmdl.api.spigot.common.modules.models.Module;
import fr.skah.skmdl.api.spigot.common.modules.models.ModuleOption;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.ConcurrentMap;

public class ModuleManager {

    @Getter
    private static final ConcurrentMap<String, Module> modules = Maps.newConcurrentMap();

    /**
     * It registers a module, calls the onStartup() function, and loads the module
     *
     * @param module The module to register.
     */
    public static void registerModule(Module module) {
        if (module != null) {
            String moduleName = module.getModuleOptions().getModuleName();

            modules.put(moduleName, module);
            loadModule(moduleName);

            module.onStartup();
            module.onEnable();

        }
    }

    /**
     * If the module is not loaded, it checks if all the dependencies are loaded, if they are, it loads the module
     *
     * @param moduleName The name of the module to load.
     */
    public static void loadModule(String moduleName) {
        Module module = modules.get(moduleName);
        for (String pluginDependency : module.getModuleOptions().getPluginDependencies()) {
            Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginDependency);
            if (plugin == null || !plugin.isEnabled()) {
                ModulesPlugin.getInstance().getLogger().warning("Impossible de charger le module " + moduleName + ". Il manque " + pluginDependency);
                return;
            }
        }
        ModulesPlugin.getInstance().getLogger().info("Le module " + moduleName + " a pu charger toutes ses dépendances  !");
    }

    /**
     * Unload the module with the name 'moduleName' by calling its onDisable() function.
     *
     * @param moduleName The name of the module.
     */
    public static void unloadModule(String moduleName) {
        modules.get(moduleName).onDisable();
    }

    /**
     * It takes a module name as a parameter, gets the module from the module manager, gets the module options, checks if
     * the module can be disabled, checks if the module is enabled, and if it is, unloads it, otherwise loads it
     *
     * @param moduleName The name of the module you want to change the state of.
     * @return The module that was changed.
     */
    public static Module changeModuleState(String moduleName) {
        Module module = ModuleManager.getModules().get(moduleName);
        ModuleOption moduleOption = module.getModuleOptions();
        if (!moduleOption.isCanBeDisabled()) return module;
        if (module.getModuleState() == ModuleState.ENABLED) ModuleManager.unloadModule(moduleName);
        else ModuleManager.loadModule(moduleOption.getModuleName());
        return module;
    }

}
