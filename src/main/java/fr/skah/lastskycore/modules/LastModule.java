package fr.skah.lastskycore.modules;

/*
 *  * @Created on 2021 - 18:00
 *  * @Project LastSkyCore
 *  * @Author Jimmy
 */

import fr.skah.lastskycore.LastSkyCore;
import fr.skah.lastskycore.api.LastCommand;
import fr.skah.lastskycore.api.LastListener;
import fr.skah.lastskycore.modules.loader.ModuleOption;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;


public abstract class LastModule {

    private ModuleOption moduleOptions;
    private ModuleState moduleState;
    private Logger logger;
    private static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

    public void onRegister() {
        setLogger(LoggerFactory.getLogger(moduleOptions.getModuleName()));
        logger.info(" Module has been registred");
    }

    public void onEnable() {
        LastSkyCore.getListeners().parallelStream().filter(e -> e.getModuleName().equals(moduleOptions.getModuleName())).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, LastSkyCore.getInstance()));
        LastSkyCore.getCommands().parallelStream().filter(e -> e.getModuleName().equals(moduleOptions.getModuleName())).forEach(command -> LastSkyCore.getInstance().getCommandManager().getCommandManager().registerCommand(command));
        setModuleState(ModuleState.ENABLED);
        printModuleInformations();
    }

    public void onDisable() {
        LastSkyCore.getListeners().parallelStream().filter(e -> e.getModuleName().equals(moduleOptions.getModuleName())).forEach(HandlerList::unregisterAll);
        LastSkyCore.getCommands().parallelStream().filter(e -> e.getModuleName().equals(moduleOptions.getModuleName())).forEach(command -> LastSkyCore.getInstance().getCommandManager().getCommandManager().unregisterCommand(command));
        getScheduledExecutorService().shutdown();
        setModuleState(ModuleState.DISABLED);
        printModuleInformations();
    }

    public void unregister() {
        onDisable();
        List<LastListener> listeners = LastSkyCore.getListeners().stream().filter(e -> e.getModuleName().equals(moduleOptions.getModuleName())).collect(Collectors.toList());
        List<LastCommand> commands = LastSkyCore.getCommands().stream().filter(e -> e.getModuleName().equals(moduleOptions.getModuleName())).collect(Collectors.toList());

        listeners.forEach(LastSkyCore.getListeners()::remove);
        commands.forEach(LastSkyCore.getCommands()::remove);
        LastSkyCore.getModulesLoaded().remove(moduleOptions.getModuleName());

        listeners.clear();
        commands.clear();
        logger.info(" Module has been unregistered");
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

    private void printModuleInformations() {
        logger.info("-----------------------------------");
        logger.info(" Name: ".concat(moduleOptions.getModuleName()));
        logger.info(" Description: ".concat(moduleOptions.getModuleDescription()));
        logger.info(" Version: ".concat(moduleOptions.getModuleVersion()));
        logger.info(" Author(s): ".concat(moduleOptions.getModuleAuthor()));
        logger.info(" Can Be Disabled: " + (moduleOptions.isCanBeDisabled() ? "yes" : "no"));
        logger.info(" State: " + (moduleState == ModuleState.ENABLED ? "Enabled" : "Disabled"));
        logger.info("-----------------------------------");
    }

    public static ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }

    public ModuleOption getModuleOptions() {
        return moduleOptions;
    }

    public void setModuleOptions(ModuleOption moduleOptions) {
        this.moduleOptions = moduleOptions;
    }

}
