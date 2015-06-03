package net.d3mok.uruk.plugins;

import net.d3mok.uruk.plugins.NUPlugin;
import net.d3mok.uruk.utils.NULocations;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class NUCommandExecutor implements CommandExecutor {

    private NUPlugin plugin;
 
    public NUCommandExecutor(NUPlugin plugin) {
        this.plugin = plugin;
    }
    
    private boolean permissionCheck(CommandSender sender, Command cmd) {
        String perm_name = cmd.getPermission();
        if (sender instanceof BlockCommandSender || sender instanceof ConsoleCommandSender) {
            // Important for scripting
            return true;
        } else if (sender instanceof Player) {
            Player p = (Player) sender;
            Boolean result = p.hasPermission(perm_name) || p.isOp();
            if (!result) {
                this.plugin.notify(sender, cmd.getPermissionMessage(), ChatColor.RED);
            }
            return result;
        }
        return false;
    }
    
    protected Location getSenderLoc(CommandSender sender) {
        Location loc = NULocations.getSenderLocation(sender);
        if (loc == null) {
            throw new CommandException("Something that was not a Player or CommandSender issued a command that requires a location (did you run it from the server console?)");
        }
        return loc;
    }

    protected boolean delegateCommand(CommandSender sender, Command cmd, String label, String[] args) {
        throw new CommandException("You must override delegateCommand in the plugin's own CommandExecutor.");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Boolean has_perm = permissionCheck(sender, cmd);
        if (!has_perm) {
            return false;
        }
        return delegateCommand(sender, cmd, label, args);
    }
    
}
