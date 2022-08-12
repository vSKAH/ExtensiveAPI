package fr.skah.skmdl.api.spigot;

/*
 *  * @Created on 2021 - 17:58
 *  * @Project SKMDL
 *  * @Author Jimmy
 */

import co.aikar.commands.PaperCommandManager;
import fr.skah.skmdl.api.commons.async.ModuleScheduler;
import fr.skah.skmdl.api.commons.mavenresolver.Dependency;
import fr.skah.skmdl.api.commons.mavenresolver.DependencyManager;
import fr.skah.skmdl.api.spigot.common.commands.CommandLoader;
import fr.skah.skmdl.api.spigot.common.hooks.Hooks;
import fr.skah.skmdl.api.spigot.common.smartinventory.InventoryManager;
import fr.skah.skmdl.api.spigot.common.modules.loader.ModuleFinder;
import fr.skah.skmdl.api.spigot.common.modules.manage.ModuleManager;
import fr.skah.skmdl.api.spigot.common.modules.models.Module;
import fr.skah.skmdl.api.spigot.common.utils.MinecraftVersion;
import fr.skah.skmdl.api.spigot.common.events.armors.ArmorListeners;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;


@Getter
public class ModulesPlugin extends JavaPlugin {

    private static ModulesPlugin instance;

    private static InventoryManager inventoryManager;
    private File dependenciesFolder;
    private CommandLoader commandLoader;

    private Hooks hooks;

    @Override
    public void onEnable() {

        //Create plugin instance
        instance = this;

        //Download load and init Dependencies.
        dependenciesFolder = new File(getDataFolder().getAbsolutePath().replace(getInstance().getName(), "SKAH-DEPENDENCIES"));
        DependencyManager dependencyManager = new DependencyManager(this.getClass());

        dependencyManager.preLoad(new Dependency("", "command-api", "", "https://repo.aikar.co/nexus/content/groups/aikar/co/aikar/acf-paper/0.5.1-SNAPSHOT/acf-paper-0.5.1-20211222.025603-2.jar", true));
        dependencyManager.preLoad(new Dependency("com.fasterxml.jackson.core", "jackson-core", "2.13.2"));
        dependencyManager.preLoad(new Dependency("com.fasterxml.jackson.core", "jackson-databind", "2.13.2.2"));
        dependencyManager.preLoad(new Dependency("com.fasterxml.jackson.core", "jackson-annotations", "2.13.2"));

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
        ModuleScheduler.shutdownNow();
    }

    public static ModulesPlugin getInstance() {
        return instance;
    }

    public static InventoryManager getInventoryManager() {
        return inventoryManager;
    }


}
