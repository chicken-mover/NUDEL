package net.d3mok.uruk.jehova;

import net.d3mok.uruk.plugins.NUCommandExecutor;
import net.d3mok.uruk.utils.NULocations;

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

public class JehovaCommandExecutor extends NUCommandExecutor {

    private Jehova plugin;
    private Logger logger;
 
    public JehovaCommandExecutor(Jehova plugin) {
        super(plugin);
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
            loc = NULocations.getLocation(senderLoc.getWorld(), args[0], args[1], args[2]);
            
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
    public boolean delegateCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        String command_name = cmd.getName();

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
