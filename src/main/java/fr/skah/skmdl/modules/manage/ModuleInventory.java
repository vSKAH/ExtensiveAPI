package fr.skah.skmdl.modules.manage;

/*
 *  * @Created on 2021 - 16:45
 *  * @Project SKMDL
 *  * @Author jimmy  / vSKAH#0075
 */

import fr.skah.skmdl.api.itemstack.ItemBuilder;
import fr.skah.skmdl.api.smartinventory.ClickableItem;
import fr.skah.skmdl.api.smartinventory.SmartInventory;
import fr.skah.skmdl.api.smartinventory.content.InventoryContents;
import fr.skah.skmdl.api.smartinventory.content.InventoryProvider;
import fr.skah.skmdl.modules.enums.ModuleState;
import fr.skah.skmdl.modules.models.Module;
import fr.skah.skmdl.modules.models.ModuleOption;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Map;

public class ModuleInventory implements InventoryProvider {


    private final SmartInventory inventory;

    public ModuleInventory() {
        inventory = SmartInventory.builder().id("modules").provider(this).size(4, 9).title(ChatColor.GOLD + "Modules").build();
    }


    private ItemBuilder getModuleItem(String moduleName, String moduleDescription, String moduleVersion, ModuleState moduleState, boolean isBeDisabled) {
        ItemBuilder itemBuilder = new ItemBuilder(Material.PAPER);
        itemBuilder.name(moduleState == ModuleState.ENABLED ? "§a".concat(moduleName) : "§c".concat(moduleName));
        itemBuilder.addLore(" ");
        itemBuilder.addLore("§f" + moduleDescription);
        itemBuilder.addLore("§f" + moduleVersion);
        itemBuilder.addLore(" ");
        String state = "§fÉtat du module : ";
        itemBuilder.addLore(moduleState == ModuleState.ENABLED ? state.concat("§aActivé") : state.concat("§cDésactivé"));
        String deactivate = "§fEst désactivable : ";
        itemBuilder.addLore(isBeDisabled ? deactivate.concat("§aOui") : deactivate.concat("§cNon"));
        return itemBuilder;
    }


    @Override
    public void init(Player player, InventoryContents contents) {
        for (Map.Entry<String, Module> moduleEntry : ModuleManager.getModules().entrySet()) {
            Module module = moduleEntry.getValue();
            ModuleOption moduleOption = module.getModuleOptions();
            contents.add(ClickableItem.of(getModuleItem(moduleEntry.getKey(), moduleOption.getModuleDescription(), moduleOption.getModuleVersion(), module.getModuleState(), moduleOption.isCanBeDisabled()).build(), this::onClick));
        }
    }


    private void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (!event.getCurrentItem().hasItemMeta() || !event.getCurrentItem().getItemMeta().hasDisplayName()) return;
        String moduleName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
        Module module = ModuleManager.changeModuleState(moduleName);
        ModuleOption moduleOption = module.getModuleOptions();
        event.getInventory().setItem(event.getSlot(), getModuleItem(moduleName, moduleOption.getModuleDescription(), moduleOption.getModuleVersion(), module.getModuleState(), moduleOption.isCanBeDisabled()).build());
        ((Player) event.getWhoClicked()).updateInventory();
    }

    @Override
    public void update(Player player, InventoryContents contents) {
        InventoryProvider.super.update(player, contents);
    }

    public SmartInventory getInventory() {
        return inventory;
    }

}
