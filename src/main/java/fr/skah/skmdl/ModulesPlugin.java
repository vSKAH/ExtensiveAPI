package fr.skah.skmdl;

/*
 *  * @Created on 2021 - 17:58
 *  * @Project SKMDL
 *  * @Author Jimmy
 */

import co.aikar.commands.PaperCommandManager;
import fr.skah.skmdl.api.spigot.commands.CommandLoader;
import fr.skah.skmdl.api.spigot.events.ArmorListeners;
import fr.skah.skmdl.api.spigot.hooks.Hooks;
import fr.skah.skmdl.api.commons.mavenresolver.Dependency;
import fr.skah.skmdl.api.commons.mavenresolver.DependencyManager;
import fr.skah.skmdl.api.spigot.smartinventory.InventoryManager;
import fr.skah.skmdl.api.spigot.modules.loader.ModuleFinder;
import fr.skah.skmdl.api.spigot.modules.manage.ModuleManager;
import fr.skah.skmdl.api.spigot.modules.models.Module;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ModulesPlugin extends JavaPlugin {

    private static ModulesPlugin instance;

    private static InventoryManager inventoryManager;
    private static File dependenciesFolder;
    private static CommandLoader commandLoader;

    private Hooks hooks;

    @Override
    public void onEnable() {
        //Create plugin instance
        instance = this;
        //Download load and init Dependencies.
        dependenciesFolder = new File(getDataFolder().getAbsolutePath().replace(getInstance().getName(), "SKAH-DEPENDENCIES"));
        DependencyManager dependencyManager = new DependencyManager(this.getClass());

        dependencyManager.preLoad(new Dependency("", "command-api", "", "https://repo.aikar.co/nexus/content/groups/aikar/co/aikar/acf-paper/0.5.1-SNAPSHOT/acf-paper-0.5.1-20211222.025603-2.jar", true));
        dependencyManager.preLoad(new Dependency("com.fasterxml.jackson.core", "jackson-core", "2.13.1"));
        dependencyManager.preLoad(new Dependency("com.fasterxml.jackson.core", "jackson-databind", "2.13.1"));
        dependencyManager.preLoad(new Dependency("com.fasterxml.jackson.core", "jackson-annotations", "2.13.1"));

        dependencyManager.dl(getDependenciesFolder()).injectJar(getDependenciesFolder());

        //Init SmartInventory
        inventoryManager = new InventoryManager(this);
        inventoryManager.init();

        //Init Aikar commands and register defaults settings
        commandLoader = new CommandLoader(new PaperCommandManager(this));
        commandLoader.registerDefault();

        //Register armor equit event
        Bukkit.getPluginManager().registerEvents(new ArmorListeners(), this);

        //Hook basics plugins
        hooks = new Hooks();


        //register and load Modules
        ModuleFinder.getAllModules().parallelStream().forEach(ModuleManager::registerModule);

    }


    @Override
    public void onDisable() {
        ModuleManager.getModules().values().parallelStream().forEach(Module::onUnregister);
    }

    public static ModulesPlugin getInstance() {
        return instance;
    }

    public Hooks getHooks() {
        return hooks;
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
