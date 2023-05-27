package fr.skoupi.extensiveapi.minecraft.hooks.basics;

import dev.unnm3d.rediseconomy.api.RedisEconomyAPI;
import dev.unnm3d.rediseconomy.currency.Currency;
import fr.skoupi.extensiveapi.minecraft.hooks.Hook;


public class RedisEconomyHook implements Hook<RedisEconomyAPI> {

    private RedisEconomyAPI redisEconomyAPI;

    @Override
    public String getHookName() {
        return "RedisEconomy";
    }

    @Override
    public String getClasz() {
        return "dev.unnm3d.rediseconomy.RedisEconomyPlugin";
    }


    @Override
    public boolean registerHook() {
        if (!pluginEnabled()) return false;
        redisEconomyAPI = RedisEconomyAPI.getAPI();
        return true;
    }

    @Override
    public RedisEconomyAPI get() {
        return redisEconomyAPI;
    }

    public Currency getCurrency(String currencyName) {
        return get().getCurrencyByName(currencyName);
    }
}
