package fr.skah.skmdl.api.spigot.common.smartinventory.utils;

/*
 *  * @Created on 12/08/2022
 *  * @Project SKMDL
 *  * @Author Jimmy  / SKAH#7513
 */


import fr.skah.skmdl.api.spigot.common.itemstack.ItemBuilder;
import fr.skah.skmdl.api.spigot.common.smartinventory.ClickableItem;
import fr.skah.skmdl.api.spigot.common.smartinventory.content.InventoryContents;
import fr.skah.skmdl.api.spigot.common.smartinventory.content.SlotPos;
import org.bukkit.Material;

public class InventoryTemplate {
    public static void addCornersTemplate(InventoryContents contents, Material mat, int data, String name) {
        contents.set(SlotPos.of(0, 0), ClickableItem.empty((new ItemBuilder(mat)).data(data).name(name).build()));
        contents.set(SlotPos.of(0, 1), ClickableItem.empty((new ItemBuilder(mat)).data(data).name(name).build()));
        contents.set(SlotPos.of(1, 0), ClickableItem.empty((new ItemBuilder(mat)).data(data).name(name).build()));
        contents.set(SlotPos.of(0, 7), ClickableItem.empty((new ItemBuilder(mat)).data(data).name(name).build()));
        contents.set(SlotPos.of(0, 8), ClickableItem.empty((new ItemBuilder(mat)).data(data).name(name).build()));
        contents.set(SlotPos.of(1, 8), ClickableItem.empty((new ItemBuilder(mat)).data(data).name(name).build()));
        contents.set(SlotPos.of(4, 0), ClickableItem.empty((new ItemBuilder(mat)).data(data).name(name).build()));
        contents.set(SlotPos.of(5, 0), ClickableItem.empty((new ItemBuilder(mat)).data(data).name(name).build()));
        contents.set(SlotPos.of(5, 1), ClickableItem.empty((new ItemBuilder(mat)).data(data).name(name).build()));
        contents.set(SlotPos.of(4, 8), ClickableItem.empty((new ItemBuilder(mat)).data(data).name(name).build()));
        contents.set(SlotPos.of(5, 8), ClickableItem.empty((new ItemBuilder(mat)).data(data).name(name).build()));
        contents.set(SlotPos.of(5, 7), ClickableItem.empty((new ItemBuilder(mat)).data(data).name(name).build()));
    }
}
