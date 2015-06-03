package net.d3mok.uruk.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.RegionContainer;


public class NULocations {

    public static Entity getEntity(CommandSender sender) {
        if (sender instanceof BlockCommandSender) {
            Block b = ((BlockCommandSender) sender).getBlock();
            return (Entity) b;
        } else if (sender instanceof Entity) {
            return (Entity) sender;
        } else {
            return null;
        }
    }

    public static Location getSenderLocation(CommandSender sender) {
        Entity e = getEntity(sender);
        if (e == null) {
            // Sender was the console.
            return null;
        }
        return e.getLocation();
    }

    public static Location getLocation(World w, String x, String y, String z) {
        Double xd = Double.valueOf(x);
        Double yd = Double.valueOf(y);
        Double zd = Double.valueOf(z);
        return new Location(w, xd, yd, zd);
    };

    public static boolean closeEnough(Location a, Location b) {
        if (a.getWorld() != b.getWorld()) {
            return false;
        }
        Double delta_x = Math.abs(a.getX() - b.getX());
        Double delta_y = Math.abs(a.getY() - b.getY());
        Double delta_z = Math.abs(a.getZ() - b.getZ());
        return delta_x < 1 && delta_y < 1 && delta_z < 1;
    }

    public static ApplicableRegionSet locationInRegions(Location loc, WorldGuardPlugin worldGuard) {
        World world = loc.getWorld();
        RegionContainer container = worldGuard.getRegionContainer();
        Vector pt = BukkitUtil.toVector(loc);
        RegionManager manager = container.get(world);
        ApplicableRegionSet regions = manager.getApplicableRegions(pt);
        return regions;
    }

    public static ApplicableRegionSet playerInRegions(Player player, WorldGuardPlugin worldGuard) {
        Location loc = player.getLocation();
        return locationInRegions(loc, worldGuard);
    }

}