package fr.skah.skmdl.api.spigot.common.commands;

/*
 *  * @Created on 2021 - 14:07
 *  * @Project SKMDL
 *  * @Author Jimmy  / vSKAH#0075
 */

import co.aikar.commands.PaperCommandManager;
import fr.skah.skmdl.api.spigot.common.modules.manage.ModuleCommand;
import fr.skah.skmdl.api.spigot.common.modules.manage.ModuleManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;

import java.util.Locale;
import java.util.stream.Collectors;

public class CommandLoader{

    private final PaperCommandManager paperCommandManager;

    public CommandLoader(PaperCommandManager paperCommandManager) {
        super();
        this.paperCommandManager = paperCommandManager;
    }

    public void registerDefault() {
        paperCommandManager.enableUnstableAPI("help");
        paperCommandManager.getCommandCompletions().registerAsyncCompletion("players", c -> Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList()));
        paperCommandManager.getCommandCompletions().registerAsyncCompletion("modules", handler -> ModuleManager.getModules().keySet());
        paperCommandManager.registerCommand(new ModuleCommand());
        paperCommandManager.getLocales().setDefaultLocale(Locale.FRANCE);
    }

    public PaperCommandManager getPaperCommandManager() {
        return paperCommandManager;
    }
}
