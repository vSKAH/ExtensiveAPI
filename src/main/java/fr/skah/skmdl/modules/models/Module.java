package fr.skah.skmdl.modules.models;

/*
 *  * @Created on 2021 - 18:00
 *  * @Project SKMDL
 *  * @Author Jimmy
 */

import co.aikar.commands.BaseCommand;
import fr.skah.skmdl.ModulesPlugin;
import fr.skah.skmdl.modules.enums.ModuleState;
import fr.skah.skmdl.modules.manage.ModuleManager;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public abstract class Module {

    private ModuleOption moduleOptions;
    private ModuleState moduleState;
    private Logger logger;

    private final Set<Listener> listeners = new HashSet<>();
    private final Set<BaseCommand> commands = new HashSet<>();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

    public void onStartup() {
        setLogger(LoggerFactory.getLogger(moduleOptions.getModuleName()));
    }

    public void onEnable() {
        listeners.parallelStream().forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, ModulesPlugin.getInstance()));
        commands.parallelStream().forEach(command -> ModulesPlugin.getInstance().getCommandManager().paperCommandManager().registerCommand(command));
        setModuleState(ModuleState.ENABLED);
        printModuleInformation();
    }

    public void onDisable() {
        listeners.parallelStream().forEach(HandlerList::unregisterAll);
        commands.parallelStream().forEach(command -> ModulesPlugin.getInstance().getCommandManager().paperCommandManager().unregisterCommand(command));
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

    public Set<Listener> getListeners() {
        return listeners;
    }

    public Set<BaseCommand> getCommands() {
        return commands;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }
}
