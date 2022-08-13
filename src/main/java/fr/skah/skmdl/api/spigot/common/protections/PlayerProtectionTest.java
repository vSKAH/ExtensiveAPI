package fr.skah.skmdl.api.spigot.common.protections;

/*
 *  * @Created on 2021 - 14:25
 *  * @Project SKMDL
 *  * @Author jimmy  / vSKAH#0075
 */

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import fr.skah.skmdl.api.spigot.ModulesPlugin;
import fr.skah.skmdl.api.spigot.common.hooks.Hooks;
import fr.skah.skmdl.api.spigot.common.utils.MinecraftVersion;
import me.angeschossen.lands.api.integration.LandsIntegration;
import me.angeschossen.lands.api.land.Land;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;


public class PlayerProtectionTest {

    public final Hooks hooks;

    public PlayerProtectionTest() {
        this.hooks = ModulesPlugin.getInstance().getHooks();
    }

    /**
     * If the block is not bedrock, liquid, a sign, a banner, or wood, then return the result of the testBreak function
     *
     * @param location The location of the block you want to break.
     * @param player The player who is breaking the block
     * @return A boolean value.
     */
    public boolean testBreakHammer(Location location, Player player) {
        final Block block = location.getBlock();

        final Material material = block.getType();
        final String materialName = material.name();

        // It's checking if the server is running on a version of Minecraft that is lower than 1.13.
        if (!MinecraftVersion.atLeast(MinecraftVersion.V.v1_13)) {
            // It's checking if the block is bedrock, liquid, or a spawner.
            if (material == Material.BEDROCK || block.isLiquid() || material == Material.SPAWNER) return false;
        }
        // It's checking if the server is running on a version of Minecraft that is at least 1.13.
        else if (material == Material.BEDROCK || block.isLiquid() || material == Material.getMaterial("MOB_SPAWNER")) return false;
        if (material == Material.CHEST || material == Material.TRAPPED_CHEST) return false;

        if (materialName.contains("AIR")) return false;
        if (materialName.contains("_SIGN") || materialName.contains("_WALL_SIGN") || materialName.contains("_SIGN_POST"))
            return false;

        if (materialName.contains("_BANNER") || materialName.contains("_WALL_BANNER")) return false;
        if (materialName.contains("WOOD") || materialName.contains("OAK") || materialName.contains("LOG")) return false;
        return testBreak(location, player);
    }

    /**
     * If the block is not air, liquid, or a chest, and the player is allowed to break it, return true
     *
     * @param location The location of the block that is being broken.
     * @param player The player who is breaking the block
     * @return A boolean value.
     */
    public boolean testBreak(Location location, Player player) {

        if (location.getWorld().getName().equalsIgnoreCase("SPAWN")) return false;

        final Block block = location.getBlock();

        if (block.isLiquid() || block.getType() == Material.CHEST) return false;

        // Checking if the plugin is hooked to Lands.
        if (hooks.isHooked("Lands")) {
            LandsIntegration landsIntegration = (LandsIntegration) hooks.getLoaded().get("Lands").get();
            Land land = landsIntegration.getLand(location);
            if (landsIntegration.isClaimed(location) && land != null && !land.getOnlinePlayers().contains(player))
                return false;
        }

        // Checking if the plugin is hooked to WorldGuard.
        if (hooks.isHooked("WorldGuard")) {
            WorldGuardPlugin worldGuardIntegration = (WorldGuardPlugin) hooks.getLoaded().get("WorldGuard").get();
            if (!worldGuardIntegration.createProtectionQuery().testBlockBreak(null, block)) return false;
        }

        return block.getType() != Material.AIR;
    }


    /**
     * If WorldGuard is loaded, return whether the player can be damaged
     *
     * @param player The player to test.
     * @return A boolean value, true = can be damaged.
     */
    public boolean testPvp(Player player) {
        // It's checking if the plugin is hooked to WorldGuard.
        if (hooks.isHooked("WorldGuard")) {
            WorldGuardPlugin worldGuardIntegration = (WorldGuardPlugin) hooks.getLoaded().get("WorldGuard").get();
            return worldGuardIntegration.createProtectionQuery().testEntityDamage(null, player);
        }
        return true;
    }

}
