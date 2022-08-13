package fr.skah.skmdl.api.spigot.common.commands;

/*
 *  * @Created on 2021 - 14:07
 *  * @Project SKMDL
 *  * @Author Jimmy  / vSKAH#0075
 */

import co.aikar.commands.PaperCommandManager;
import fr.skah.skmdl.api.spigot.common.modules.manage.ModuleCommand;
import fr.skah.skmdl.api.spigot.common.modules.manage.ModuleManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;

import java.util.Locale;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CommandLoader{

    @Getter
    // It's a variable that will be used to register commands.
    private final PaperCommandManager paperCommandManager;


    /**
     * We enable the `help` command, we register a completion for the `players` argument, we register a completion for the
     * `modules` argument, we register the `ModuleCommand` command, and we set the default locale to `Locale.FRANCE`
     */
    public void registerDefault() {
        paperCommandManager.enableUnstableAPI("help");
        paperCommandManager.getCommandCompletions().registerAsyncCompletion("players", c -> Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList()));
        paperCommandManager.getCommandCompletions().registerAsyncCompletion("modules", handler -> ModuleManager.getModules().keySet());
        paperCommandManager.registerCommand(new ModuleCommand());
        paperCommandManager.getLocales().setDefaultLocale(Locale.FRANCE);
    }
}
