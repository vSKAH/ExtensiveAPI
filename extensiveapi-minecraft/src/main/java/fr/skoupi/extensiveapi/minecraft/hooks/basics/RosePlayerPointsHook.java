package fr.skoupi.extensiveapi.minecraft.hooks.basics;

/*  RosePlayerPoints
 *  By: vSKAH <vskahhh@gmail.com>

 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */

import fr.skoupi.extensiveapi.minecraft.hooks.Hook;
import org.black_ixx.playerpoints.PlayerPoints;

public class RosePlayerPointsHook implements Hook<PlayerPoints> {

	private PlayerPoints playerPoints;

	@Override
	public String getHookName ()
	{
		return "PlayerPoints";
	}

	@Override
	public String getClasz ()
	{
		return "org.black_ixx.playerpoints.PlayerPoints";
	}

	/**
	 * If Vault is enabled, get the Economy plugin that Vault is using
	 *
	 * @return A boolean value.
	 */
	@Override
	public boolean registerHook ()
	{
		if (!pluginEnabled()) return false;
		playerPoints = PlayerPoints.getInstance();
		return true;
	}

	/**
	 * This function returns the Economy object that is used by Vault.
	 *
	 * @return The vaultEconomy object.
	 */
	@Override
	public PlayerPoints get ()
	{
		return playerPoints;
	}

}
