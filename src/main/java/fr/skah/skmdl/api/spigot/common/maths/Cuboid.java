package fr.skah.skmdl.api.spigot.common.maths;

/*
 *  * @Created on 08/05/2022
 *  * @Project AresiaKoth
 *  * @Author Jimmy  / SKAH#7513
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.List;

public class Cuboid implements Serializable {

    private String worldName;

    private int x1, y1, z1;
    private int x2, y2, z2;

    public Cuboid() {
    }

    //Cuboid from raw values
    private Cuboid(String string, int n, int n2, int n3, int n4, int n5, int n6) {
        this.worldName = string;
        this.x1 = Math.min(n, n4);
        this.x2 = Math.max(n, n4);
        this.y1 = Math.min(n2, n5);
        this.y2 = Math.max(n2, n5);
        this.z1 = Math.min(n3, n6);
        this.z2 = Math.max(n3, n6);
    }

    //Cuboid from cuboid
    public Cuboid(Cuboid cuboid) {
        this(cuboid.getWorldName(), cuboid.getX1(), cuboid.getY1(), cuboid.getZ1(), cuboid.getX2(), cuboid.getY2(), cuboid.getZ2());
    }

    //Cuboid from location
    public Cuboid(Location location1, Location location2) {
        this.worldName = location1.getWorld().getName();
        this.x1 = Math.min(location1.getBlockX(), location2.getBlockX());
        this.y1 = Math.min(location1.getBlockY(), location2.getBlockY());
        this.z1 = Math.min(location1.getBlockZ(), location2.getBlockZ());
        this.x2 = Math.max(location1.getBlockX(), location2.getBlockX());
        this.y2 = Math.max(location1.getBlockY(), location2.getBlockY());
        this.z2 = Math.max(location1.getBlockZ(), location2.getBlockZ());
    }


    //Check if X Y Z is inside the cuboid
    public boolean contains(int x, int y, int z) {
        return x >= this.x1 && x <= this.x2 && y >= this.y1 && y <= this.y2 && z >= this.z1 && z <= this.z2;
    }

    //Check if a location is inside the cuboid
    public boolean contains(Location location) {
        return this.worldName.equals(location.getWorld().getName()) && this.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    //Check if a player is inside the cuboid
    public boolean contains(Player player) {
        return this.contains(player.getLocation());
    }

    //Check all player inside the cuboid and return list of them
    @JsonIgnore
    public List<Player> getPlayersInside() {
        List<Player> list = Lists.newArrayList();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (this.contains(onlinePlayer)) list.add(onlinePlayer);
        }
        return list;
    }


    public String getWorldName() {
        return worldName;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getZ1() {
        return z1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public int getZ2() {
        return z2;
    }

    @Override
    public String toString() {
        return "Cuboid{" +
                "worldName='" + worldName + '\'' +
                ", x1=" + x1 +
                ", y1=" + y1 +
                ", z1=" + z1 +
                ", x2=" + x2 +
                ", y2=" + y2 +
                ", z2=" + z2 +
                '}';
    }
}
