package fr.skah.skmdl.api.spigot.common.modules.models;

/*
 *  * @Created on 2021 - 18:00
 *  * @Project SKMDL
 *  * @Author Jimmy
 */

import co.aikar.commands.BaseCommand;
import fr.skah.skmdl.api.spigot.ModulesPlugin;
import fr.skah.skmdl.api.spigot.common.modules.enums.ModuleState;
import fr.skah.skmdl.api.spigot.common.modules.manage.ModuleManager;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public abstract class Module {

    private ModuleOption moduleOptions;
    private String moduleFileName;

    private ModuleState moduleState;
    private Logger logger;
    private File moduleConfigurationFolder;

    private final Set<Listener> listeners = new HashSet<>();
    private final Set<BaseCommand> commands = new HashSet<>();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

    public void onStartup() {
        setLogger(LoggerFactory.getLogger(moduleOptions.getModuleName()));
        moduleConfigurationFolder = new File(ModulesPlugin.getInstance().getDataFolder(), "modules/".concat(moduleOptions.getModuleName()));
    }

    public void onEnable() {
        ModulesPlugin plugin = ModulesPlugin.getInstance();
        listeners.parallelStream().forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, plugin));
        commands.parallelStream().forEach(command -> plugin.getCommandManager().getPaperCommandManager().registerCommand(command));
        setModuleState(ModuleState.ENABLED);
        printModuleInformation();
    }

    public void onDisable() {
        listeners.parallelStream().forEach(HandlerList::unregisterAll);
        commands.parallelStream().forEach(command -> ModulesPlugin.getInstance().getCommandManager().getPaperCommandManager().unregisterCommand(command));
        getScheduledExecutorService().shutdown();
        setModuleState(ModuleState.DISABLED);
        printModuleInformation();
    }

    public void onUnregister() {
        onDisable();
        listeners.clear();
        commands.clear();
        ModuleManager.getModules().remove(moduleOptions.getModuleName());
    }

    public ModuleState getModuleState() {
        return moduleState;
    }

    public void setModuleState(ModuleState moduleState) {
        this.moduleState = moduleState;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    private void printModuleInformation() {
        logger.info("-----------------------------------");
        logger.info(" Name: ".concat(moduleOptions.getModuleName()));
        logger.info(" Description: ".concat(moduleOptions.getModuleDescription()));
        logger.info(" Version: ".concat(moduleOptions.getModuleVersion()));
        logger.info(" Author(s): ".concat(moduleOptions.getModuleAuthor()));
        logger.info(" Can Be Disabled: " + (moduleOptions.isCanBeDisabled() ? "yes" : "no"));
        logger.info(" State: " + (moduleState == ModuleState.ENABLED ? "Enabled" : "Disabled"));
        logger.info("-----------------------------------");
    }

    public ModuleOption getModuleOptions() {
        return moduleOptions;
    }

    public void setModuleOptions(ModuleOption moduleOptions) {
        this.moduleOptions = moduleOptions;
    }

    public File getModuleConfigurationFolder() {
        return moduleConfigurationFolder;
    }

    public Set<Listener> getListeners() {
        return listeners;
    }

    public Set<BaseCommand> getCommands() {
        return commands;
    }

    public String getModuleFileName() {
        return moduleFileName;
    }

    public void setModuleFileName(String moduleFileName) {
        this.moduleFileName = moduleFileName;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }
}
