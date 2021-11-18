package fr.skah.skmdl;

/*
 *  * @Created on 2021 - 17:58
 *  * @Project SKMDL
 *  * @Author Jimmy
 */

import co.aikar.commands.PaperCommandManager;
import fr.skah.skmdl.api.commands.CommandLoader;
import fr.skah.skmdl.api.mavenresolver.Dependency;
import fr.skah.skmdl.api.mavenresolver.DependencyManager;
import fr.skah.skmdl.api.smartinventory.InventoryManager;
import fr.skah.skmdl.modules.loader.ModuleFinder;
import fr.skah.skmdl.modules.manage.ModuleManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ModulesPlugin extends JavaPlugin {

    private static ModulesPlugin instance;

    private DependencyManager dependencyManager;

    private static InventoryManager inventoryManager;
    private static File dependenciesFolder;
    private static CommandLoader commandLoader;

    @Override
    public void onEnable() {
        //Create plugin instance
        instance = this;
        //Download load and init Dependencies.
        dependenciesFolder = new File(getDataFolder().getAbsolutePath().replace(getInstance().getName(), "SKAH-DEPENDENCIES"));
        dependencyManager = new DependencyManager(this.getClass());
        loadCoreDependencies();
        getDependencyManager().dl(getDependenciesFolder()).injectJar(getDependenciesFolder());
        //Init SmartInventory
        inventoryManager = new InventoryManager(this);
        inventoryManager.init();

        //Init Aikar commands and register defaults settings
        commandLoader = new CommandLoader(new PaperCommandManager(this));
        commandLoader.registerDefault();

        //register and load Modules
        ModuleFinder.getAllModules().forEach(ModuleManager::registerModule);
    }

    @Override
    public void onDisable() {
        ModuleManager.getModules().values().parallelStream().forEach(module -> {
            module.onDisable();
            module.onUnregister();
        });
    }

    private void loadCoreDependencies() {
        dependencyManager.preLoad(new Dependency("", "command-api", "", "https://repo.aikar.co/nexus/content/groups/aikar/co/aikar/acf-paper/0.5.0-SNAPSHOT/acf-paper-0.5.0-20210210.142912-169.jar", true));
        dependencyManager.preLoad(new Dependency("com.fasterxml.jackson.core", "jackson-core", "2.8.0"));
        dependencyManager.preLoad(new Dependency("com.fasterxml.jackson.core", "jackson-databind", "2.8.0"));
        dependencyManager.preLoad(new Dependency("com.fasterxml.jackson.core", "jackson-annotations", "2.8.0"));
    }

    public static ModulesPlugin getInstance() {
        return instance;
    }

    public DependencyManager getDependencyManager() {
        return dependencyManager;
    }

    public File getDependenciesFolder() {
        return dependenciesFolder;
    }

    public static InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public CommandLoader getCommandManager() {
        return commandLoader;
    }
}
