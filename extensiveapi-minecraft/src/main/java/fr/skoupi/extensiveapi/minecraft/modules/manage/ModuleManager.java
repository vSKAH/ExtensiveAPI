package fr.skoupi.extensiveapi.minecraft.modules.manage;

/*  ModuleManager
 *  By: vSKAH <vskahhh@gmail.com>

 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */

import com.google.common.collect.Maps;
import fr.skoupi.extensiveapi.minecraft.ModulesPlugin;
import fr.skoupi.extensiveapi.minecraft.modules.enums.ModuleState;
import fr.skoupi.extensiveapi.minecraft.modules.exceptions.ModuleDependencyException;
import fr.skoupi.extensiveapi.minecraft.modules.exceptions.ModuleEnablingException;
import fr.skoupi.extensiveapi.minecraft.modules.exceptions.ModuleStartupException;
import fr.skoupi.extensiveapi.minecraft.modules.loader.ModuleFinder;
import fr.skoupi.extensiveapi.minecraft.modules.models.Module;
import fr.skoupi.extensiveapi.minecraft.modules.models.ModuleOption;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ModuleManager {

    @Getter
    private static final ConcurrentMap<String, Module> modules = Maps.newConcurrentMap();

    /**
     * It registers a module, calls the onStartup() function, and loads the module
     *
     * @param module The module to register.
     */
    public static void registerModule(Module module) throws ModuleEnablingException, ModuleStartupException, ModuleDependencyException {
        if (module != null) {
            String moduleName = module.getModuleOptions().getModuleName();
            if (!getModules().containsKey(moduleName)) {
                getModules().put(moduleName, module);
                if (loadPluginDependency(moduleName)) {
                    module.onStartup();
                    module.onEnable();
                }
            }
        }
    }

    public static void registerModules() {
        ConcurrentLinkedQueue<Module> allModules = ModuleFinder.getAllModules();
        AtomicInteger taskId = new AtomicInteger();

        taskId.set(Bukkit.getScheduler().runTaskTimerAsynchronously(ModulesPlugin.getInstance(), new BukkitRunnable() {
            @Override
            public void run() {
                Module module = allModules.poll();

                if (module == null) {
                    ModulesPlugin.getInstance().setLoadingIsDone(true);
                    Bukkit.getScheduler().cancelTask(taskId.get());
                    return;
                }
                if (!ModuleManager.getModules().containsKey(module.getModuleOptions().getModuleName())) {
                    try {
                        ModuleManager.registerModule(module);
                    } catch (ModuleEnablingException | ModuleStartupException | ModuleDependencyException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, 20 * 5, 10).getTaskId());
    }

    /**
     * If the module is not loaded, it checks if all the dependencies are loaded, if they are, it loads the module
     *
     * @param moduleName The name of the module to load.
     */
    private static boolean loadPluginDependency(String moduleName) throws ModuleDependencyException, ModuleStartupException, ModuleEnablingException {
        Module module = modules.get(moduleName);
        for (String pluginDependency : module.getModuleOptions().getPluginDependencies()) {
            Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginDependency);
            if (plugin == null || !plugin.isEnabled()) {
                throw new ModuleDependencyException(moduleName, pluginDependency);
            }
        }

        for (String modulesDependency : module.getModuleOptions().getModulesDependencies()) {
            for (Module allModule : ModuleFinder.getAllModules()) {
                if (modulesDependency.equals(allModule.getModuleOptions().getModuleName())) {
                    registerModule(allModule);
                }
            }
        }
        return true;
    }


    /**
     * It takes a module name as a parameter, gets the module from the module manager, gets the module options, checks if
     * the module can be disabled, checks if the module is enabled, and if it is, unloads it, otherwise loads it
     *
     * @param moduleName The name of the module you want to change the state of.
     * @return The module that was changed.
     */
    public static Module toggleModule(String moduleName) throws ModuleDependencyException, ModuleEnablingException, ModuleStartupException {
        Module module = ModuleManager.getModules().get(moduleName);
        ModuleOption moduleOption = module.getModuleOptions();
        if (!moduleOption.isCanBeDisabled()) return module;
        if (module.getModuleState() == ModuleState.ENABLED) modules.get(moduleName).onDisable();
        else if (module.getModuleState() == ModuleState.DISABLED) {
            if (ModuleManager.loadPluginDependency(moduleOption.getModuleName())) {
                module.onEnable();
            }
        }
        return module;
    }

}
