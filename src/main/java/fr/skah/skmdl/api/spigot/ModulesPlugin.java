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
import fr.skah.skmdl.api.data.mongo.MongoDataSource;
import fr.skah.skmdl.api.spigot.common.commands.CommandLoader;
import fr.skah.skmdl.api.spigot.common.hooks.Hooks;
import fr.skah.skmdl.api.spigot.common.smartinventory.InventoryManager;
import fr.skah.skmdl.api.spigot.common.modules.loader.ModuleFinder;
import fr.skah.skmdl.api.spigot.common.modules.manage.ModuleManager;
import fr.skah.skmdl.api.spigot.common.modules.models.Module;
import fr.skah.skmdl.api.spigot.common.events.armors.ArmorListeners;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;


@Getter
public class ModulesPlugin extends JavaPlugin {

    private static ModulesPlugin instance;

    private static InventoryManager inventoryManager;
    private DependencyManager dependencyManager;
    private File dependenciesFolder;
    private CommandLoader commandLoader;

    private Hooks hooks;

    private MongoDataSource mongoDataSource;

    /**
     * > We create a new instance of the plugin, download and load dependencies, init SmartInventory, init Aikar commands,
     * register armor equit event, hook basics plugins, register and load Modules
     */
    @Override
    public void onEnable() {

        //Create plugin instance
        instance = this;

        //Download load and init Dependencies.
        dependenciesFolder = new File(getDataFolder().getAbsolutePath().replace(getInstance().getName(), "SKAH-DEPENDENCIES"));
        dependencyManager = new DependencyManager(this.getClass());

        //Download from custom repository
        dependencyManager.preLoad(new Dependency("io.papermc", "paperlib", "1.0.7", "https://papermc.io/repo/repository/maven-public/", false));
        dependencyManager.preLoad(new Dependency("", "command-api", "", "https://repo.aikar.co/nexus/content/groups/aikar/co/aikar/acf-paper/0.5.1-SNAPSHOT/acf-paper-0.5.1-20211222.025603-2.jar", true));

        //Download from maven central
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
        for (Module allModule : ModuleFinder.getAllModules()) {
            ModuleManager.registerModule(allModule);
        }

    }

    public void registerMongoDataSource(String hostname) {
        if (mongoDataSource == null || !mongoDataSource.getMongoHostname().equalsIgnoreCase(hostname)) {
            mongoDataSource = new MongoDataSource(hostname);
            mongoDataSource.openDataSource();
            getLogger().info("MongoDataSource has enabled ! ");
        }
    }


    /**
     * When the plugin is disabled, unregister all modules and shutdown the scheduler.
     */
    @Override
    public void onDisable() {
        if(mongoDataSource != null && mongoDataSource.dataSourceIsOpen()) mongoDataSource.closeDataSource();
        ModuleManager.getModules().values().forEach(Module::onUnregister);
        ModuleScheduler.shutdownNow();
    }

    /**
     * If the instance variable is null, create a new ModulesPlugin object and assign it to the instance variable. Then
     * return the instance variable.
     *
     * @return The instance of the ModulesPlugin class.
     */
    public static ModulesPlugin getInstance() {
        return instance;
    }

    /**
     * This function returns the inventoryManager variable.
     *
     * @return The inventoryManager object.
     */
    public static InventoryManager getInventoryManager() {
        return inventoryManager;
    }


}
