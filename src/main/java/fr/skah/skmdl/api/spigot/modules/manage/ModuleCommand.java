package fr.skah.skmdl.api.spigot.modules.manage;

/*
 *  * @Created on 2021 - 13:27
 *  * @Project SKMDL
 *  * @Author Jimmy  / vSKAH#0075
 */

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import fr.skah.skmdl.api.spigot.modules.loader.ModuleFinder;
import fr.skah.skmdl.api.spigot.modules.models.Module;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

@CommandAlias("Module")
public class ModuleCommand extends BaseCommand {

    @Default
    @CommandPermission("skmdl.modules.manage")
    @Description("Affiche la liste des commandes liées aux modules")
    public void manageModules(CommandSender sender) {
        sender.sendMessage("§7§m-----------------------------------");
        sender.sendMessage("§6>> §eModule unregister [Nom du Module]");
        sender.sendMessage("§6>> §eModule register [Nom du Module]");
        sender.sendMessage("§6>> §eModules");
        sender.sendMessage("§7§m-----------------------------------");

    }

    @CommandCompletion("@modules")
    @Subcommand("register")
    @CommandPermission("skmdl.modules.manage")
    @Description("Permet de charger un module")
    public void registerModule(CommandSender sender, String moduleName) {
        String moduleWithJar = moduleName.concat(".jar");
        if (ModuleManager.getModules().get(moduleName) != null) {
            sender.sendMessage("Le module est déjà chargé !");
            return;
        }
        ModuleManager.registerModule(Objects.requireNonNull(ModuleFinder.getModuleFromFile(moduleWithJar)));
        sender.sendMessage("Le module vient d'être chargé !");
    }


    @CommandCompletion("@modules")
    @Subcommand("unregister")
    @CommandPermission("skmdl.modules.manage")
    @Description("Permet de décharger un module")
    public void unregisterModule(CommandSender sender, String moduleName) {

        final Module module = ModuleManager.getModules().get(moduleName);

        if (module == null) {
            sender.sendMessage("Le module n'est pas chargé !");
            return;
        }

        if (!module.getModuleOptions().isCanBeDisabled()) {
            sender.sendMessage("Le module ne peut pas être désactivé !");
            return;
        }

        module.onUnregister();
        sender.sendMessage("Le module vient d'être déchargé !");
    }




    @CommandAlias("Modules")
    @CommandPermission("skmdl.modules.manage")
    @Description("Permet de d'activer / désactiver un module")
    public void openGuiMenu(Player player) {
        new ModuleInventory().getInventory().open(player);
    }




}
