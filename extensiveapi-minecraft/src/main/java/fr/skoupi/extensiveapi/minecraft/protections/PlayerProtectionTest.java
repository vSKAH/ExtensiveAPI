package fr.skoupi.extensiveapi.minecraft.protections;

/*  PlayerProtectionTest
 *  By: vSKAH <vskahhh@gmail.com>

 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import fr.skoupi.extensiveapi.minecraft.ExtensiveCore;
import fr.skoupi.extensiveapi.minecraft.hooks.Hooks;
import fr.skoupi.extensiveapi.minecraft.utils.MinecraftVersion;
import lombok.NonNull;
import me.angeschossen.lands.api.integration.LandsIntegration;
import me.angeschossen.lands.api.land.Land;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;


public class PlayerProtectionTest {

	public final Hooks hooks;

	public PlayerProtectionTest ()
	{
		this.hooks = ExtensiveCore.getInstance().getHooks();
	}

	/**
	 * If the block is not bedrock, liquid, a sign, a banner, or wood, then return the result of the testBreak function
	 *
	 * @param location The location of the block you want to break.
	 * @param player   The player who is breaking the block
	 * @return A boolean value.
	 */
	public boolean testBreakHammer (Location location, Player player)
	{
		final Block block = location.getBlock();

		final Material material = block.getType();
		final String materialName = material.name();

		if (material == Material.BEDROCK) return false;

		if (materialName.contains("SIGN") || materialName.contains("BANNER") || materialName.contains("WOOD") || materialName.contains("OAK") || materialName.contains("LOG"))
			return false;

		// It's checking if the server is running on a version of Minecraft that is lower than 1.13.
		// AND It's checking if the block is a spawner
		if ((!MinecraftVersion.atLeast(MinecraftVersion.V.v1_13)) && material == Material.SPAWNER) return false;

			// It's checking if the server is running on a version of Minecraft that is at least 1.13.
			// AND It's checking if the block is a spawner
		else if (material == Material.getMaterial("MOB_SPAWNER")) return false;

		return testBreak(location, player);
	}

	/**
	 * If the block is not air, liquid, or a chest, and the player is allowed to break it, return true
	 *
	 * @param location The location of the block that is being broken.
	 * @param player   The player who is breaking the block
	 * @return A boolean value.
	 */
	public boolean testBreak (@NonNull Location location, Player player)
	{
		final Block block = location.getBlock();

		if (block.isLiquid() || block.getType().name().contains("CHEST")) return false;

		// Checking if the plugin is hooked to Lands.
		if (hooks.isHooked("Lands"))
		{
			LandsIntegration landsIntegration = (LandsIntegration) hooks.getLoaded().get("Lands").get();
			Land land = landsIntegration.getLand(location);
			if (landsIntegration.isClaimed(location) && land != null && !land.getOnlinePlayers().contains(player))
				return false;
		}

		// Checking if the plugin is hooked to WorldGuard.
		if (hooks.isHooked("WorldGuard"))
		{
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
	public boolean testPvp (Player player)
	{
		// It's checking if the plugin is hooked to WorldGuard.
		if (hooks.isHooked("WorldGuard"))
		{
			WorldGuardPlugin worldGuardIntegration = (WorldGuardPlugin) hooks.getLoaded().get("WorldGuard").get();
			return worldGuardIntegration.createProtectionQuery().testEntityDamage(null, player);
		}
		return true;
	}

}
