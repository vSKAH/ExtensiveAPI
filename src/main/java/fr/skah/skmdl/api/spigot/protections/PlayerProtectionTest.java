package fr.skah.skmdl.api.spigot.protections;

/*
 *  * @Created on 2021 - 14:25
 *  * @Project SKMDL
 *  * @Author jimmy  / vSKAH#0075
 */

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import fr.skah.skmdl.api.spigot.ModulesPlugin;
import fr.skah.skmdl.api.spigot.hooks.Hooks;
import me.angeschossen.lands.api.integration.LandsIntegration;
import me.angeschossen.lands.api.land.Land;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PlayerProtectionTest {

    public final Hooks hooks;

    public PlayerProtectionTest() {
        this.hooks = ModulesPlugin.getInstance().getHooks();
    }

    public boolean testBreak(Location location, Player player) {

        final Block block = location.getBlock();

        if(block.isLiquid() || !block.getType().isOccluding() || block.getType() != Material.CHEST) return false;

        if (hooks.isHooked("Lands")) {
            LandsIntegration landsIntegration = (LandsIntegration) hooks.getLoaded().get("Lands").get();
            Land land = landsIntegration.getLand(location);
            if (landsIntegration.isClaimed(location) && land != null && !land.getOnlinePlayers().contains(player))
                return false;
        }

        if (hooks.isHooked("WorldGuard")) {
            WorldGuardPlugin worldGuardIntegration = (WorldGuardPlugin) hooks.getLoaded().get("WorldGuard").get();
            if (!worldGuardIntegration.createProtectionQuery().testBlockBreak(null, block)) return false;
        }

        return block.getType() != Material.AIR;
    }


    public boolean testPvp(Entity entity, Player player) {
        if (hooks.isHooked("WorldGuard")) {
            WorldGuardPlugin worldGuardIntegration = (WorldGuardPlugin) hooks.getLoaded().get("WorldGuard").get();
            return worldGuardIntegration.createProtectionQuery().testEntityDamage(null, entity);
        }
        return true;
    }

}
