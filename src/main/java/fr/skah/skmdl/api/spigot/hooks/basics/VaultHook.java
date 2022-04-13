package fr.skah.skmdl.api.spigot.hooks.basics;

/*
 *  * @Created on 2021 - 11:45
 *  * @Project SKMDL
 *  * @Author jimmy  / vSKAH#0075
 */

import fr.skah.skmdl.api.spigot.hooks.Hook;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook implements Hook<Economy> {

    private Economy vaultEconomy;

    @Override
    public String getHookName() {
        return "Vault";
    }

    @Override
    public String getClasz() {
        return "net.milkbowl.vault.economy.Economy";
    }

    @Override
    public boolean registerHook() {
        if(!pluginEnabled()) return false;
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;
        vaultEconomy = rsp.getProvider();
        return true;
    }

    @Override
    public Economy get() {
        return vaultEconomy;
    }

}
