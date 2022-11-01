package fr.skoupi.extensiveapi.minecraft.commands;

/*  CommandLoader
 *  By: vSKAH <vskahhh@gmail.com>

 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */

import co.aikar.commands.PaperCommandManager;
import fr.skoupi.extensiveapi.minecraft.modules.manage.ModuleCommand;
import fr.skoupi.extensiveapi.minecraft.modules.manage.ModuleManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;

import java.util.Locale;
import java.util.stream.Collectors;

public record CommandLoader(@Getter PaperCommandManager paperCommandManager) {

    /**
     * We enable the `help` command, we register a completion for the `players` argument, we register a completion for the
     * `modules` argument, we register the `ModuleCommand` command, and we set the default locale to `Locale.FRANCE`
     */
    public void registerDefault ()
    {
        paperCommandManager.enableUnstableAPI("help");
        paperCommandManager.getCommandCompletions().registerAsyncCompletion("players", c -> Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList()));
        paperCommandManager.getCommandCompletions().registerAsyncCompletion("modules", handler -> ModuleManager.getModules().keySet());
        paperCommandManager.registerCommand(new ModuleCommand());
        paperCommandManager.getLocales().setDefaultLocale(Locale.FRANCE);
    }
}
