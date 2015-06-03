package net.d3mok.uruk.plugins;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;


public class NUPlugin extends JavaPlugin {

    public PluginManager getPluginManager() {
        return getServer().getPluginManager();
    }
    
    public WorldGuardPlugin getWorldGuard() {
        Plugin w = getPluginManager().getPlugin("WorldGuard");
        if (w == null) {
            throw new RuntimeException("Can't load WorldGuard!");
        }
        return (WorldGuardPlugin) w;
    }
    
    public WorldEditPlugin getWorldEdit() {
        Plugin w = getPluginManager().getPlugin("WorldEdit");
        if (w == null) {
            throw new RuntimeException("Can't load WorldEdit!");
        }
        return (WorldEditPlugin) w;
    }

    public void notify(CommandSender sender, String msg, ChatColor color) {
        sender.sendMessage("["+getName()+"] "+color+msg);
        getLogger().info("Notified "+String.valueOf(sender)+": "+msg);
    }

    public void notify(CommandSender sender, String msg) {
        notify(sender, msg, ChatColor.WHITE);
    }

    public void notify(Player player, String msg, ChatColor color) {
        notify((CommandSender) player, msg, color);
    }

    public void notify(Player player, String msg) {
        notify(player, msg, ChatColor.WHITE);
    }
    
}