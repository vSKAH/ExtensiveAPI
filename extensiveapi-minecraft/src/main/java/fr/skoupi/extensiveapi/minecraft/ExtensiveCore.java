package fr.skoupi.extensiveapi.minecraft;

/*  ModulesPlugin
 *  By: vSKAH <vskahhh@gmail.com>

 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */

import co.aikar.commands.BaseCommand;
import co.aikar.commands.PaperCommandManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.skoupi.extensiveapi.core.configuration.ConfigurationExporter;
import fr.skoupi.extensiveapi.core.mavenresolver.Dependency;
import fr.skoupi.extensiveapi.core.mavenresolver.DependencyManager;
import fr.skoupi.extensiveapi.minecraft.commands.CommandLoader;
import fr.skoupi.extensiveapi.minecraft.hooks.Hooks;

import fr.skoupi.extensiveapi.minecraft.smartinventory.InventoryManager;
import fr.skoupi.extensiveapi.minecraft.armors.ArmorListeners;
import fr.skoupi.extensiveapi.minecraft.utils.ExtensiveThreadPool;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Getter
public class ExtensiveCore extends JavaPlugin {

    private static ExtensiveCore instance;
    private static InventoryManager inventoryManager;
    private CommandLoader commandLoader;
    private Hooks hooks;

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
        getDataFolder().mkdirs();

        //Download load and init Dependencies.
        File dependenciesFile = new File(getDataFolder(), "dependencies.json");
        File dependenciesFolder = new File(getDataFolder(), "SKAH-DEPENDENCIES");

        DependencyManager dependencyManager = new DependencyManager(this.getClass());

        try {
            if (!dependenciesFile.exists()) {
                ConfigurationExporter.createConfig(dependenciesFile, getClass().getResourceAsStream("/dependencies.json"), false);
            }
            Type listOfMyClassObject = new TypeToken<ArrayList<Dependency>>() {}.getType();

            Gson gson = new Gson();
            List<Dependency> outputList = gson.fromJson(FileUtils.readFileToString(dependenciesFile, "UTF-8"), listOfMyClassObject);

            for (Dependency dependency : outputList) {
                dependencyManager.preLoad(dependency);
                getLogger().info("Loading dependency: " + dependency.getArtifactId() + " v" + dependency.getVersion());
            }
            getLogger().info("Downloading dependencies...");
            dependencyManager.dl(dependenciesFolder);
            getLogger().info("Injecting dependencies...");
            dependencyManager.injectJar(dependenciesFolder);
            getLogger().info("Dependencies are loaded and injected !");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


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

        //Hook basics plugins
        hooks = new Hooks();

        if (useArmorEvent)
            Bukkit.getPluginManager().registerEvents(new ArmorListeners(), this);

        ExtensiveThreadPool.RUNNABLE_EXECUTOR.scheduleAtFixedRate(new CommandLoader.unregisterCommandTask(), 5, 5, TimeUnit.SECONDS);
    }


    /**
     * When the plugin is disabled, unregister all modules and shutdown the scheduler.
     */
    @Override
    public void onDisable() {
        ExtensiveThreadPool.shutdownNow();
    }

    /**
     * If the instance variable is null, create a new ModulesPlugin object and assign it to the instance variable. Then
     * return the instance variable.
     *
     * @return The instance of the ModulesPlugin class.
     */
    public static ExtensiveCore getInstance() {
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


    public void registerCommand(BaseCommand baseCommand) {
        commandLoader.getPaperCommandManager().registerCommand(baseCommand);
    }

    public void registerCommands(BaseCommand... baseCommands) {
        for (BaseCommand baseCommand : baseCommands) {
            registerCommand(baseCommand);
        }
    }

    public void registerListener(JavaPlugin plugin, Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }

    public void registerListeners(JavaPlugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            registerListener(plugin, listener);
        }
    }
}
