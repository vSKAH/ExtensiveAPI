package fr.skoupi.extensiveapi.minecraft.hooks.basics;

import com.massivecraft.factions.FactionsPlugin;
import fr.skoupi.extensiveapi.minecraft.hooks.Hook;

public class FactionHook implements Hook<FactionsPlugin> {

    private FactionsPlugin factions;

    @Override
    public boolean registerHook() {
        if(!pluginEnabled()) return false;
        this.factions = FactionsPlugin.getInstance();
        return true;
    }

    @Override
    public String getHookName() {
        return "Factions";
    }

    @Override
    public String getClasz() {
        return "com.massivecraft.factions.FactionsPlugin";
    }

    @Override
    public FactionsPlugin get() {
        return factions;
    }
}
