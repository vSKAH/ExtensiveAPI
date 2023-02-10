package fr.skoupi.extensiveapi.minecraft.listeners;

/*  CancelConnectionEvent
 * By: vSKAH <vskahhh@gmail.com>
 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */

import fr.skoupi.extensiveapi.minecraft.ModulesPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class CancelConnectionEvent implements Listener {


    @EventHandler
    private void onPlayerPreJoin(AsyncPlayerPreLoginEvent event) {
        if(!ModulesPlugin.getInstance().isLoadingIsDone()) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "§cChargement des modules.");
        }
    }

}
