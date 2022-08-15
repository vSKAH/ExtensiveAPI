package fr.skah.skmdl.api.spigot.common.liteobjects;

import lombok.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class LiteLocation {

    private String worldName;

    private int x;
    private int y;
    private int z;

    public Location toBukkitLocation() {
        return new Location(Bukkit.getWorld(getWorldName()), getX(), getY(), getZ());
    }

    public LiteLocation fromBukkitLocation(Location location) {
        this.setWorldName(location.getWorld().getName());
        this.setX(location.getBlockX());
        this.setY(location.getBlockY());
        this.setZ(location.getBlockZ());
        return this;
    }
}
