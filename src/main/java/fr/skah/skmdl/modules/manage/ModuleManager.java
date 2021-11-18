package fr.skah.skmdl.modules.manage;

/*
 *  * @Created on 2021 - 17:22
 *  * @Project SKMDL
 *  * @Author jimmy  / vSKAH#0075
 */

import com.google.common.collect.Maps;
import fr.skah.skmdl.modules.enums.ModuleState;
import fr.skah.skmdl.modules.models.Module;
import fr.skah.skmdl.modules.models.ModuleOption;

import java.util.Map;

public class ModuleManager {

    private static final Map<String, Module> modules = Maps.newHashMap();


    public static void registerModule(Module module) {
        module.onStartup();
        module.onEnable();
        modules.put(module.getModuleOptions().getModuleName(), module);
    }

    public static void loadModule(String moduleName) {
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

    public static Map<String, Module> getModules() {
        return modules;
    }
}
