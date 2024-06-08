package fr.skoupi.extensiveapi.minecraft.smartinventory.content;

import fr.skoupi.extensiveapi.minecraft.smartinventory.ClickableItem;
import fr.skoupi.extensiveapi.minecraft.smartinventory.config.structs.DummyItem;
import fr.skoupi.extensiveapi.minecraft.smartinventory.config.structs.GuiSettings;
import org.bukkit.entity.Player;

public abstract class InventoryProvider {

    protected void placeDummyItems(Player player, InventoryContents contents, GuiSettings guiSettings) {
        for (DummyItem dummyItem : guiSettings.getDummyItems()) {
            for (int slot : dummyItem.getSlot()) {
                SlotPos slotPos = new SlotPos(slot);
                if (dummyItem.getCommands() == null || dummyItem.getCommands().isEmpty())
                    contents.set(slotPos, ClickableItem.empty(dummyItem.getItem()));
                else
                    contents.set(slotPos, ClickableItem.of(dummyItem.getItem(), event -> {
                        for (String command : dummyItem.getCommands()) {
                            player.performCommand(command.replace("%player%", player.getName()));
                        }
                    }));
            }
        }
    }

    public void init(Player player, InventoryContents contents) {}
    public void update(Player player, InventoryContents contents) {}

}
