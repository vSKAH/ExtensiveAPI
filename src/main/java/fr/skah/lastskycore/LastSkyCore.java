package fr.skah.lastskycore;

/*
 *  * @Created on 2021 - 17:58
 *  * @Project LastSkyCore
 *  * @Author Jimmy
 */

import fr.skah.lastskycore.api.inventory.FastInvManager;
import fr.skah.lastskycore.api.LastCommand;
import fr.skah.lastskycore.api.LastListener;
import fr.skah.lastskycore.commands.CommandLoader;
import fr.skah.lastskycore.mavenresolver.Dependency;
import fr.skah.lastskycore.mavenresolver.DependencyManager;
import fr.skah.lastskycore.modules.LastModule;
import fr.skah.lastskycore.modules.loader.ModuleLoader;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public class LastSkyCore extends JavaPlugin {

    private static LastSkyCore instance;
    private static final HashMap<String, LastModule> MODULES_LOADED = new HashMap<>();
    private static final Set<LastListener> LISTENERS = new HashSet<>();
    private static final Set<LastCommand> COMMANDS = new HashSet<>();

    private DependencyManager dependencyManager;
    private static File dependenciesFolder;

    private static CommandLoader commandLoader;

    @Override
    public void onEnable() {
        //Create plugin instance
        instance = this;
        //Download load and init Dependencies.
        dependenciesFolder = new File(getDataFolder().getAbsolutePath().replace(getInstance().getName(), "SKAH-DEPENDENCIES"));
        dependencyManager = new DependencyManager(this.getClass());
        loadCoreDependences();
        getDependencyManager().dl(getDependenciesFolder()).injectJar(getDependenciesFolder());
        //Init FastInv
        FastInvManager.register(this);
        //Init Aikar commands
        commandLoader = new CommandLoader();
        //register and load Modules
        new ModuleLoader().registerModules();
        MODULES_LOADED.values().parallelStream().forEach(LastModule::onEnable);
    }

    @Override
    public void onDisable() {
        MODULES_LOADED.values().parallelStream().forEach(LastModule::onDisable);
    }

    private void loadCoreDependences() {
        dependencyManager.preLoad(new Dependency("", "command-api", "", "https://repo.aikar.co/nexus/content/groups/aikar/co/aikar/acf-paper/0.5.0-SNAPSHOT/acf-paper-0.5.0-20210210.142912-169.jar", true));

        dependencyManager.preLoad(new Dependency("com.fasterxml.jackson.core", "jackson-core", "2.8.0"));
        dependencyManager.preLoad(new Dependency("com.fasterxml.jackson.core", "jackson-databind", "2.8.0"));
        dependencyManager.preLoad(new Dependency("com.fasterxml.jackson.core", "jackson-annotations", "2.8.0"));
    }

    public static LastSkyCore getInstance() {
        return instance;
    }

    public DependencyManager getDependencyManager() {
        return dependencyManager;
    }

    public File getDependenciesFolder() {
        return dependenciesFolder;
    }

    public static HashMap<String, LastModule> getModulesLoaded() {
        return MODULES_LOADED;
    }

    public static Set<LastListener> getListeners() {
        return LISTENERS;
    }

    public static Set<LastCommand> getCommands() {
        return COMMANDS;
    }

    public CommandLoader getCommandManager() {
        return commandLoader;
    }
}
