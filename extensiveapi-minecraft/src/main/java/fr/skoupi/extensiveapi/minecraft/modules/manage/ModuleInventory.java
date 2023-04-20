package fr.skoupi.extensiveapi.minecraft.modules.manage;

/*  ModuleInventory
 *  By: vSKAH <vskahhh@gmail.com>

 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */

import fr.skoupi.extensiveapi.minecraft.itemstack.ItemBuilder;
import fr.skoupi.extensiveapi.minecraft.modules.exceptions.ModuleDependencyException;
import fr.skoupi.extensiveapi.minecraft.modules.exceptions.ModuleEnablingException;
import fr.skoupi.extensiveapi.minecraft.modules.exceptions.ModuleStartupException;
import fr.skoupi.extensiveapi.minecraft.smartinventory.ClickableItem;
import fr.skoupi.extensiveapi.minecraft.smartinventory.SmartInventory;
import fr.skoupi.extensiveapi.minecraft.smartinventory.content.InventoryContents;
import fr.skoupi.extensiveapi.minecraft.smartinventory.content.InventoryProvider;
import fr.skoupi.extensiveapi.minecraft.modules.enums.ModuleState;
import fr.skoupi.extensiveapi.minecraft.modules.models.Module;
import fr.skoupi.extensiveapi.minecraft.modules.models.ModuleOption;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Map;

public class ModuleInventory implements InventoryProvider {


    @Getter
    private final SmartInventory inventory;

    // It's creating a new inventory with the id "modules" and the title "Modules"
    public ModuleInventory() {
        inventory = SmartInventory.builder().id("modules").provider(this).size(4, 9).title(ChatColor.GOLD + "Modules").build();
    }


    /**
     * It returns an ItemBuilder object that contains the module's name, description, version, state and if it can be
     * disabled
     *
     * @param moduleName The name of the module
     * @param moduleDescription The description of the module
     * @param moduleVersion The version of the module
     * @param moduleState The state of the module (enabled or disabled)
     * @param isBeDisabled If the module is disabled, it will be displayed in red.
     * @return ItemBuilder
     */
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


    /**
     * It loops through all the modules and adds them to the inventory
     *
     * @param player The player who opened the inventory
     * @param contents The contents of the inventory.
     */
    @Override
    public void init(Player player, InventoryContents contents) {
        for (Map.Entry<String, Module> moduleEntry : ModuleManager.getModules().entrySet()) {
            Module module = moduleEntry.getValue();
            ModuleOption moduleOption = module.getModuleOptions();
            contents.add(ClickableItem.of(getModuleItem(moduleEntry.getKey(), moduleOption.getModuleDescription(), moduleOption.getModuleVersion(), module.getModuleState(), moduleOption.isCanBeDisabled()).build(), this::onClick));
        }
    }


    /**
     * When a player clicks on a module in the module menu, the module's state is changed and the module's item is updated
     * to reflect the change
     *
     * @param event The InventoryClickEvent that was called.
     */
    private void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (!event.getCurrentItem().hasItemMeta()) return;
        String moduleName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
        Module module;
        try {
            module = ModuleManager.toggleModule(moduleName);
        } catch (ModuleDependencyException | ModuleEnablingException | ModuleStartupException e) {
            throw new RuntimeException(e);
        }
        ModuleOption moduleOption = module.getModuleOptions();
        event.getInventory().setItem(event.getSlot(), getModuleItem(moduleName, moduleOption.getModuleDescription(), moduleOption.getModuleVersion(), module.getModuleState(), moduleOption.isCanBeDisabled()).build());
        ((Player) event.getWhoClicked()).updateInventory();
    }

    /**
     * This function is called every time the inventory is opened.
     *
     * @param player The player who opened the inventory
     * @param contents The InventoryContents object that contains all the information about the inventory.
     */
    @Override
    public void update(Player player, InventoryContents contents) {
        InventoryProvider.super.update(player, contents);
    }

}
