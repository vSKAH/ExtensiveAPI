package fr.skah.skmdl.api.spigot.common.cooldown;

/*
 *  * @Created on 2022 - 16:58
 *  * @Project items-module
 *  * @Author jimmy  / vSKAH#0075
 */

import fr.skah.skmdl.api.commons.cooldown.Cooldown;
import fr.skah.skmdl.api.spigot.ModulesPlugin;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class PlayerCooldown implements Cooldown<Player> {
    @Override
    // It's adding a timer to the player.
    public void addTimer(Player player, String cooldownIdentifier, long time) {
        player.setMetadata(cooldownIdentifier, new FixedMetadataValue(ModulesPlugin.getInstance(), (time * 1000) + System.currentTimeMillis()));
    }

    @Override
    // It's checking if the player has a timer.
    public boolean isInTimer(Player player, String cooldownIdentifier) {
        return player.hasMetadata(cooldownIdentifier) && player.getMetadata(cooldownIdentifier).size() > 0 && player.getMetadata(cooldownIdentifier).get(0).asLong() > System.currentTimeMillis();
    }

    @Override
    // It's getting the time left of the timer.
    public long getTime(Player player, String cooldownIdentifier) {
        return !player.hasMetadata(cooldownIdentifier) ? 0 : (player.getMetadata(cooldownIdentifier).get(0).asLong() - System.currentTimeMillis()) / 1000;
    }
}
