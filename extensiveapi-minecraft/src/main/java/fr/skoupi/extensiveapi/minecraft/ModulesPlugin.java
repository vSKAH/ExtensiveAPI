package fr.skoupi.extensiveapi.minecraft;

/*  ModulesPlugin
 *  By: vSKAH <vskahhh@gmail.com>

 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */

import co.aikar.commands.PaperCommandManager;
import fr.skoupi.extensiveapi.core.mavenresolver.Dependency;
import fr.skoupi.extensiveapi.core.mavenresolver.DependencyManager;
import fr.skoupi.extensiveapi.databases.mongodb.MongoDataSource;
import fr.skoupi.extensiveapi.minecraft.commands.CommandLoader;
import fr.skoupi.extensiveapi.minecraft.hooks.Hooks;
import fr.skoupi.extensiveapi.minecraft.listeners.CancelConnectionEvent;
import fr.skoupi.extensiveapi.minecraft.modules.ModuleScheduler;
import fr.skoupi.extensiveapi.minecraft.modules.exceptions.ModuleDependencyException;
import fr.skoupi.extensiveapi.minecraft.modules.exceptions.ModuleEnablingException;
import fr.skoupi.extensiveapi.minecraft.modules.exceptions.ModuleStartupException;
import fr.skoupi.extensiveapi.minecraft.modules.loader.ModuleFinder;
import fr.skoupi.extensiveapi.minecraft.smartinventory.InventoryManager;
import fr.skoupi.extensiveapi.minecraft.modules.manage.ModuleManager;
import fr.skoupi.extensiveapi.minecraft.modules.models.Module;
import fr.skoupi.extensiveapi.minecraft.armors.ArmorListeners;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Getter
public class ModulesPlugin extends JavaPlugin {

    private static ModulesPlugin instance;

    private static InventoryManager inventoryManager;
    private DependencyManager dependencyManager;
    private File dependenciesFolder;
    private CommandLoader commandLoader;

    private Hooks hooks;

    private MongoDataSource mongoDataSource;
    @Getter
    @Setter
    private boolean loadingIsDone;

    @Getter
    @Setter
    private boolean useArmorEvent = false;

    /**
     * > We create a new instance of the plugin, download and load dependencies,
     */
    @Override
    public void onLoad() {
        //Create plugin instance
        instance = this;

        //Download load and init Dependencies.
        dependenciesFolder = new File(getDataFolder().getAbsolutePath().replace(getInstance().getName(), "SKAH-DEPENDENCIES"));
        dependencyManager = new DependencyManager(this.getClass());

        //Download from custom repository
        dependencyManager.preLoad(new Dependency("io.papermc", "paperlib", "1.0.7", "https://papermc.io/repo/repository/maven-public/", false));
        dependencyManager.preLoad(new Dependency("", "command-api", "", "https://repo.aikar.co/nexus/content/groups/aikar/co/aikar/acf-paper/0.5.1-SNAPSHOT/acf-paper-0.5.1-20211222.025603-2.jar", true));

        //Download Jackson from maven central
        dependencyManager.preLoad(new Dependency("com.fasterxml.jackson.core", "jackson-core", "2.14.0"));
        dependencyManager.preLoad(new Dependency("com.fasterxml.jackson.core", "jackson-annotations", "2.14.0"));
        dependencyManager.preLoad(new Dependency("com.fasterxml.jackson.core", "jackson-databind", "2.14.0"));
        dependencyManager.preLoad(new Dependency("com.fasterxml.jackson.dataformat", "jackson-dataformat-yaml", "2.14.0"));

        dependencyManager.preLoad(new Dependency("org.mongodb", "mongodb-driver-sync", "4.7.1"));
        dependencyManager.preLoad(new Dependency("org.mongodb", "bson", "4.7.1"));
        dependencyManager.preLoad(new Dependency("org.mongodb", "mongodb-driver-core", "4.7.1"));
        dependencyManager.preLoad(new Dependency("org.mongodb", "bson-record-codec", "4.7.1"));

        dependencyManager.preLoad(new Dependency("org.redisson", "redisson", "3.20.0"));
        dependencyManager.preLoad(new Dependency("org.yaml", "snakeyaml", "1.26"));
        dependencyManager.dl(getDependenciesFolder()).injectJar(getDependenciesFolder());
    }

    /**
     * > We init SmartInventory, init Aikar commands,
     * register armor equit event, hook basics plugins, register and load Modules
     */
    @Override
    public void onEnable() {
        //Init SmartInventory
        inventoryManager = new InventoryManager(this);
        inventoryManager.init();

        //Init Aikar commands and register defaults settings
        commandLoader = new CommandLoader(new PaperCommandManager(this));
        commandLoader.registerDefault();


        Bukkit.getPluginManager().registerEvents(new CancelConnectionEvent(), this);

        //Hook basics plugins
        hooks = new Hooks();

        //register and load Modules
        ModuleManager.registerModules();


        if (useArmorEvent)
            Bukkit.getPluginManager().registerEvents(new ArmorListeners(), this);

    }

    public void registerMongoDataSource(String hostname) {
        if (mongoDataSource == null) {
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
        if (mongoDataSource != null && mongoDataSource.dataSourceIsOpen()) mongoDataSource.closeDataSource();
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
