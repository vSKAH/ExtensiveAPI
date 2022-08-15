package fr.skah.skmdl.api.spigot.common.modules.manage;

/*
 *  * @Created on 2021 - 13:27
 *  * @Project SKMDL
 *  * @Author Jimmy  / vSKAH#0075
 */

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import fr.skah.skmdl.api.spigot.common.modules.loader.ModuleFinder;
import fr.skah.skmdl.api.spigot.common.modules.models.Module;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

@CommandAlias("Module")
public class ModuleCommand extends BaseCommand {

    /**
     * It displays a list of commands related to modules
     *
     * @param sender The CommandSender who executed the command.
     */
    @Default
    @CommandPermission("skmdl.modules.manage")
    @Description("Affiche la liste des commandes liées aux modules")
    public void manageModules(CommandSender sender) {
        sender.sendMessage("§7§m-----------------------------------");
        sender.sendMessage("§6>> §eModule unregister [Nom du Module]");
        sender.sendMessage("§6>> §eModule register [Nom du Module]");
        sender.sendMessage("§6>> §eModule reload [Nom du Module]");
        sender.sendMessage("§6>> §eModules");
        sender.sendMessage("§7§m-----------------------------------");
    }

    /**
     * It loads a module from a jar file
     *
     * @param sender     The CommandSender who executed the command.
     * @param moduleName The name of the module to register.
     */
    @CommandCompletion("@modules")
    @Subcommand("register")
    @CommandPermission("skmdl.modules.manage")
    @Description("Permet de charger un module")
    public void registerModule(CommandSender sender, String moduleName) {
        String moduleWithJar = moduleName.concat(".jar");
        if (ModuleManager.getModules().containsKey(moduleName)) {
            sender.sendMessage("Le module est déjà chargé !");
            return;
        }

        ModuleManager.registerModule(Objects.requireNonNull(ModuleFinder.getModuleFromFile(moduleWithJar)));
        sender.sendMessage("Le module vient d'être chargé !");
    }


    /**
     * This function unregisters a module.
     *
     * @param sender     The CommandSender who executed the command.
     * @param moduleName The name of the module to unregister.
     * @return A boolean
     */
    @CommandCompletion("@modules")
    @Subcommand("unregister")
    @CommandPermission("skmdl.modules.manage")
    @Description("Permet de décharger un module")
    public boolean unregisterModule(CommandSender sender, String moduleName) {

        final Module module = ModuleManager.getModules().get(moduleName);
        if (module == null) {
            sender.sendMessage("Le module n'est pas chargé !");
            return false;
        }

        if (!module.getModuleOptions().isCanBeDisabled()) {
            sender.sendMessage("Le module ne peut pas être désactivé !");
            return false;
        }

        module.onUnregister();
        sender.sendMessage("Le module vient d'être déchargé !");
        return true;
    }


    /**
     * It unregisters a module, then registers it again
     *
     * @param sender     The CommandSender who executed the command.
     * @param moduleName The name of the module to reload.
     */
    @CommandCompletion("@modules")
    @Subcommand("reload")
    @CommandPermission("skmdl.modules.manage")
    @Description("Permet de relancer un Module")
    public void reloadModule(CommandSender sender, String moduleName) {
        final Module module = ModuleManager.getModules().get(moduleName);

        if (!module.getModuleOptions().isCanBeDisabled()) {
            sender.sendMessage("Le module ne peut pas être désactivé !");
            return;
        }

        if (module != null) {
            unregisterModule(sender, moduleName);
            registerModule(sender, module.getModuleFileName().replace(".jar", ""));
        }

    }

    /**
     * Open the module inventory for the player.
     *
     * @param player The player who executed the command
     */
    @CommandAlias("Modules")
    @CommandPermission("skmdl.modules.manage")
    @Description("Permet de d'activer / désactiver un module")
    public void openGuiMenu(Player player) {
        new ModuleInventory().getInventory().open(player);
    }


}
