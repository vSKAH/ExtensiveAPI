package fr.skah.skmdl.api.commands;

/*
 *  * @Created on 2021 - 14:07
 *  * @Project SKMDL
 *  * @Author Jimmy  / vSKAH#0075
 */

import co.aikar.commands.PaperCommandManager;
import fr.skah.skmdl.modules.manage.ModuleCommand;
import fr.skah.skmdl.modules.manage.ModuleManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;

import java.util.stream.Collectors;

public record CommandLoader(PaperCommandManager paperCommandManager) {

    public void registerDefault() {
        paperCommandManager.enableUnstableAPI("help");
        paperCommandManager.getCommandCompletions().registerAsyncCompletion("players", c -> Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList()));
        paperCommandManager.getCommandCompletions().registerAsyncCompletion("modules", handler -> ModuleManager.getModules().keySet());
        paperCommandManager.registerCommand(new ModuleCommand());
    }

    @Override
    public PaperCommandManager paperCommandManager() {
        return paperCommandManager;
    }
}
