package fr.skoupi.extensiveapi.minecraft.modules.models;

/*  Module
 *  By: vSKAH <vskahhh@gmail.com>

 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */

import co.aikar.commands.BaseCommand;
import fr.skoupi.extensiveapi.minecraft.ModulesPlugin;
import fr.skoupi.extensiveapi.minecraft.modules.enums.ModuleState;
import fr.skoupi.extensiveapi.minecraft.modules.manage.ModuleManager;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
public abstract class Module {

    private ModuleOption moduleOptions;
    private String moduleFileName;
    private ModuleState moduleState;
    private Logger logger;
    private File moduleConfigurationFolder;

    private final Set<Listener> listeners = new HashSet<>();
    private final Set<BaseCommand> commands = new HashSet<>();

    /**
     * This function is called when the module is loaded.
     */
    public void onStartup() {
        setModuleState(ModuleState.STARTUP);
        setLogger(LoggerFactory.getLogger(moduleOptions.getModuleName()));
        moduleConfigurationFolder = new File(ModulesPlugin.getInstance().getDataFolder(), "modules/".concat(moduleOptions.getModuleName()));
    }

    /**
     * It registers all the listeners and commands in the module, sets the module state to enabled, and prints the module
     * information
     */
    public void onEnable() {
        ModulesPlugin plugin = ModulesPlugin.getInstance();
        listeners.parallelStream().forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, plugin));
        commands.parallelStream().forEach(command -> plugin.getCommandLoader().paperCommandManager().registerCommand(command));
        setModuleState(ModuleState.ENABLED);
        printModuleInformation();
    }

    /**
     * It unregisters all listeners, unregisters all commands, shuts down the executor service, and sets the module state
     * to disabled
     */
    public void onDisable() {
        listeners.parallelStream().forEach(HandlerList::unregisterAll);
        commands.parallelStream().forEach(command -> ModulesPlugin.getInstance().getCommandLoader().paperCommandManager().unregisterCommand(command));
        setModuleState(ModuleState.DISABLED);
        printModuleInformation();
    }

    /**
     * It removes the module from the module manager, and clears the listeners and commands
     */
    public void onUnregister() {
        onDisable();
        listeners.clear();
        commands.clear();
        ModuleManager.getModules().remove(moduleOptions.getModuleName());
    }


    /**
     * It prints the module's name, description, version, author(s), whether it can be disabled, and its current
     * state
     */
    private void printModuleInformation() {
        if (logger != null) {
            logger.info("-----------------------------------");
            logger.info(" Name: ".concat(moduleOptions.getModuleName()));
            logger.info(" Description: ".concat(moduleOptions.getModuleDescription()));
            logger.info(" Version: ".concat(moduleOptions.getModuleVersion()));
            logger.info(" Author(s): ".concat(moduleOptions.getModuleAuthor()));
            logger.info(" Can Be Disabled: " + (moduleOptions.isCanBeDisabled() ? "yes" : "no"));
            logger.info(" State: " + (moduleState == ModuleState.ENABLED ? "Enabled" : "Disabled"));
            logger.info("-----------------------------------");
        }
    }
}
