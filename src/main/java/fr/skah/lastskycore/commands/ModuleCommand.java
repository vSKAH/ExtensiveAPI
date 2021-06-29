package fr.skah.lastskycore.commands;

/*
 *  * @Created on 2021 - 13:27
 *  * @Project LastSkyCore
 *  * @Author Jimmy  / vSKAH#0075
 */

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import fr.skah.lastskycore.LastSkyCore;
import fr.skah.lastskycore.api.inventory.FastInv;
import fr.skah.lastskycore.api.inventory.ItemBuilder;
import fr.skah.lastskycore.modules.LastModule;
import fr.skah.lastskycore.modules.ModuleState;
import fr.skah.lastskycore.modules.loader.ModuleLoader;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

@CommandAlias("Module")
public class ModuleCommand extends BaseCommand {

    @Default
    @CommandCompletion("@modules")
    @Syntax("[ModuleName] [true/false]")
    @CommandPermission("lastskycore.module.manage")
    @Description("Permet de d'activer / désactiver un module")
    public void manageModules(CommandSender sender, String moduleName, boolean value) {
        manageModule(sender, moduleName, value);
    }

    @CommandCompletion("@modules")
    @Subcommand("unregister")
    @CommandPermission("lastskycore.module.manage")
    @Description("Permet de décharger un module")
    public void unregisterModule(CommandSender sender, String moduleName) {

        final LastModule lastModule = LastSkyCore.getModulesLoaded().get(moduleName);

        if (lastModule == null) {
            sender.sendMessage("Le module n'est pas chargé !");
            return;
        }

        if (!lastModule.getModuleOptions().isCanBeDisabled()) {
            sender.sendMessage("Le module ne peut pas être désactivé !");
            return;
        }

        lastModule.unregister();
        sender.sendMessage(Component.text("Le module vient d'être déchargé !"));
    }


    @CommandCompletion("@modules")
    @Subcommand("register")
    @CommandPermission("lastskycore.module.manage")
    @Description("Permet de charger un module")
    public void registerModule(CommandSender sender, String moduleName) {
        String moduleWithJar = moduleName.concat(".jar");
        if (LastSkyCore.getModulesLoaded().get(moduleName) != null) {
            sender.sendMessage("Le module est déjà chargé !");
            return;
        }
        new ModuleLoader().registerModule(moduleWithJar);
        sender.sendMessage(Component.text("Le module vient d'être chargé !"));
    }


    @CommandAlias("Modules")
    @CommandPermission("lastskycore.module.manage")
    @Description("Permet de d'activer / désactiver un module")
    public void openGuiMenu(Player player) {
        final FastInv modulesInventory = new FastInv(InventoryType.CHEST, "§2§l§nModules");
        for (LastModule lastModule : LastSkyCore.getModulesLoaded().values()) {
            modulesInventory.addItem(new ItemBuilder(Material.NAME_TAG).name(lastModule.getModuleOptions().getModuleName()).addLore("§7".concat(lastModule.getModuleOptions().getModuleDescription()), " ", "§eAuteur: " + lastModule.getModuleOptions().getModuleAuthor(), "§eVersion: " + lastModule.getModuleOptions().getModuleVersion(), "", "§fStatus: ".concat(lastModule.getModuleState().name())).build(), null);
            modulesInventory.open(player);
        }
        modulesInventory.addClickHandler(e -> {
            ItemStack clicked = e.getCurrentItem();
            if (clicked == null || clicked.getType() == Material.AIR || !clicked.hasItemMeta() || !clicked.getItemMeta().hasDisplayName()) return;
            manageModule(player, clicked.getDisplayName(), LastSkyCore.getModulesLoaded().get(clicked.getDisplayName()).getModuleState() != ModuleState.ENABLED);
        });
        modulesInventory.open(player);
    }

    private void manageModule(CommandSender sender, String moduleName, boolean value) {

        final LastModule lastModule = LastSkyCore.getModulesLoaded().get(moduleName);
        if (!lastModule.getModuleOptions().isCanBeDisabled()) {
            sender.sendMessage("Le module ne peut pas être désactivé !");
            return;
        }


        if (!value) {
            if (lastModule.getModuleState() != ModuleState.ENABLED) {
                sender.sendMessage(Component.text("Le module est déjà désactivé !"));
                return;
            }
            lastModule.onDisable();
        } else {
            if (lastModule.getModuleState() != ModuleState.DISABLED) {
                sender.sendMessage(Component.text("Le module est déjà activé !"));
                return;
            }
            lastModule.onEnable();
        }
        sender.sendMessage(Component.text(value ? "Le module ".concat(moduleName).concat(" a eté activé !") : "Le module ".concat(moduleName).concat(" a été désactivé !")));

    }

}
