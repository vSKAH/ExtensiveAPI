package fr.skah.lastskycore.commands;

/*
 *  * @Created on 2021 - 14:07
 *  * @Project LastSkyCore
 *  * @Author Jimmy  / vSKAH#0075
 */

import co.aikar.commands.PaperCommandManager;
import fr.skah.lastskycore.LastSkyCore;

public class CommandLoader {

    private final PaperCommandManager commandManager;

    public CommandLoader() {
        commandManager = new PaperCommandManager(LastSkyCore.getInstance());
        commandManager.enableUnstableAPI("help");
        commandManager.getCommandCompletions().registerAsyncCompletion("modules", c -> LastSkyCore.getModulesLoaded().keySet());
        commandManager.registerCommand(new ModuleCommand());
    }

    public PaperCommandManager getCommandManager() {
        return commandManager;
    }
}
