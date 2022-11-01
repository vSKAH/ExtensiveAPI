package fr.skoupi.extensiveapi.minecraft.hooks.basics;

/*  VaultHook
 *  By: vSKAH <vskahhh@gmail.com>

 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */

import fr.skoupi.extensiveapi.minecraft.hooks.Hook;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook implements Hook<Economy> {

	private Economy vaultEconomy;

	@Override
	public String getHookName () {
		return "Vault";
	}

	@Override
	public String getClasz () {
		return "net.milkbowl.vault.economy.Economy";
	}

	/**
	 * If Vault is enabled, get the Economy plugin that Vault is using
	 *
	 * @return A boolean value.
	 */
	@Override
	public boolean registerHook () {
		if (!pluginEnabled()) return false;
		RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) return false;
		vaultEconomy = rsp.getProvider();
		return true;
	}

	/**
	 * This function returns the Economy object that is used by Vault.
	 *
	 * @return The vaultEconomy object.
	 */
	@Override
	public Economy get () {
		return vaultEconomy;
	}

}
