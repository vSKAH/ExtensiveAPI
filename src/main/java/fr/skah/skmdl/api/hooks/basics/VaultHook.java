package fr.skah.skmdl.api.hooks.basics;

/*
 *  * @Created on 2021 - 11:45
 *  * @Project SKMDL
 *  * @Author jimmy  / vSKAH#0075
 */

import fr.skah.skmdl.api.hooks.Hook;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook implements Hook {

    private Economy vaultEconomy;

    @Override
    public String getHookName() {
        return "Vault";
    }

    @Override
    public String getClasz() {
        return null;
    }

    @Override
    public void registerHook() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return;
        vaultEconomy = rsp.getProvider();
    }

    @Override
    public Economy get() {
        return vaultEconomy;
    }

}
