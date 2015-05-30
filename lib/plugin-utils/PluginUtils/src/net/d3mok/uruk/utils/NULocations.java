package net.d3mok.uruk.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.entity.Entity;

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

}