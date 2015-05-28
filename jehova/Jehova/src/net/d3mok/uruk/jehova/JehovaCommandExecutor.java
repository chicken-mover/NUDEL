package net.d3mok.uruk.jehova;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class JehovaCommandExecutor implements CommandExecutor {

    private Jehova plugin;
    private Logger logger;
 
    public JehovaCommandExecutor(Jehova plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
    }
    
    private boolean permissionCheck(CommandSender sender, Command cmd) {
        String name = cmd.getPermission();
        if (sender instanceof BlockCommandSender || sender instanceof ConsoleCommandSender) {
            // Important for scripting
            return true;
        } else if (sender instanceof Player) {
            Player p = (Player) sender;
            Boolean result = p.hasPermission("Jehova."+name) || p.isOp();
            if (!result) {
                sender.sendMessage(ChatColor.RED + cmd.getPermissionMessage());
            }
            return result;
        }
        return false;
    }
    
    private Location getSenderLoc(CommandSender sender) {
        if (sender instanceof BlockCommandSender) {
            Block b = ((BlockCommandSender) sender).getBlock();
            return (Location) b.getLocation();
        } else if (sender instanceof Player) {
            return ((Player) sender).getLocation();
        } else {
            throw new CommandException("Something that was not a Player or CommandSender issued a command that requires a location (did you run it from the server console?)");
        }
    }
    
    private boolean strikeBoom(CommandSender sender, Command cmd, String label, String[] args, Boolean hasEffect) {
        Location loc;
        
        if (args.length == 1) {
            Server s = (Server) this.plugin.getServer();
            Player p = (Player) s.getPlayer(args[0]);
            if (p != null && p.isOnline()) {
                loc = (Location) p.getLocation();
            } else {
                return false;
            }
        } else if (args.length == 3) {
            
            Location senderLoc = getSenderLoc(sender);
            
            Double x = Double.valueOf(args[0]);
            Double y = Double.valueOf(args[1]);
            Double z = Double.valueOf(args[2]);
            
            loc = new Location((World) senderLoc.getWorld(), x, y, z);
            
        } else if (args.length == 0) {
            loc = getSenderLoc(sender);
        } else {
            // Bad argument list.
            return false;
        }
        World w = loc.getWorld();
        if (hasEffect) {
            w.strikeLightning(loc);
        } else {
            w.strikeLightningEffect(loc);
        }
        return true;
    }
    
    private boolean storm(CommandSender sender, Command cmd, String label, String[] args) {
        Boolean toggle;
        World w = null;
        
        try {
            w = (World) getSenderLoc(sender).getWorld();
        } catch (CommandException e) {
            logger.info("/storm couldn't get CommandSender location (did you call it from the console?), so defaulting to the first normal world");
            Server s = (Server) this.plugin.getServer();
            List<World> worlds = s.getWorlds();
            for (World wi : worlds) {
                // Get the first normal world we can find
                Environment env = (Environment) wi.getEnvironment();
                if (env.equals(World.Environment.NORMAL)) {
                    w = wi;
                    break;
                }
            }
            if (w == null) {
                // If no world is an overworld, your server is kinda weird.
                throw new CommandException("/storm couldn't find any normal worlds");
            }
        }
        
        if (args.length > 1) {
            return false;
        } else if (args.length == 0) {
            toggle = !w.hasStorm();
        } else if (args[0].equalsIgnoreCase("on")) {
            toggle = true;
        } else if (args[0].equalsIgnoreCase("off")) {
            toggle = false;
        } else {
            logger.info("invalid argument: '"+args[0]+"'");
            return false;
        }
        
        w.setStorm(toggle);
        return true;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        String command_name = cmd.getName();
        Boolean has_perm = permissionCheck(sender, cmd);
        
        if (!has_perm) {
            return false;
        }
        
        switch(command_name) {
            case "strike":
            case "boom":
                return strikeBoom(sender, cmd, label, args, command_name.equals("strike"));
            case "storm":
                return storm(sender, cmd, label, args);
        }
        
        return false;
    }
    
}
