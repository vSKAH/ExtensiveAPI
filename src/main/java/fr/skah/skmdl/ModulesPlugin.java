package fr.skah.skmdl;

/*
 *  * @Created on 2021 - 17:58
 *  * @Project SKMDL
 *  * @Author Jimmy
 */

import co.aikar.commands.PaperCommandManager;
import fr.skah.skmdl.api.commands.CommandLoader;
import fr.skah.skmdl.api.events.ArmorListeners;
import fr.skah.skmdl.api.mavenresolver.Dependency;
import fr.skah.skmdl.api.mavenresolver.DependencyManager;
import fr.skah.skmdl.api.smartinventory.InventoryManager;
import fr.skah.skmdl.modules.loader.ModuleFinder;
import fr.skah.skmdl.modules.manage.ModuleManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ModulesPlugin extends JavaPlugin {

    private static ModulesPlugin instance;

    private DependencyManager dependencyManager;

    private static InventoryManager inventoryManager;
    private static File dependenciesFolder;
    private static CommandLoader commandLoader;
    private static Economy econ = null;


    @Override
    public void onEnable() {
        //Create plugin instance
        instance = this;
        //Download load and init Dependencies.
        dependenciesFolder = new File(getDataFolder().getAbsolutePath().replace(getInstance().getName(), "SKAH-DEPENDENCIES"));
        dependencyManager = new DependencyManager(this.getClass());
        loadCoreDependencies();
        dependencyManager.dl(getDependenciesFolder()).injectJar(getDependenciesFolder());
        //Init SmartInventory
        inventoryManager = new InventoryManager(this);
        inventoryManager.init();

        //Init Aikar commands and register defaults settings
        commandLoader = new CommandLoader(new PaperCommandManager(this));
        commandLoader.registerDefault();

        Bukkit.getPluginManager().registerEvents(new ArmorListeners(), this);
        //register and load Modules
        ModuleFinder.getAllModules().forEach(ModuleManager::registerModule);

        //Register vault eco
        setupEconomy();
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
        dependencyManager.preLoad(new Dependency("com.fasterxml.jackson.core", "jackson-core", "2.13.0"));
        dependencyManager.preLoad(new Dependency("com.fasterxml.jackson.core", "jackson-databind", "2.13.0"));
        dependencyManager.preLoad(new Dependency("com.fasterxml.jackson.core", "jackson-annotations", "2.13.0"));
    }


    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static ModulesPlugin getInstance() {
        return instance;
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

    public static Economy getEcon() {
        return econ;
    }
}
