package net.d3mok.uruk.scrapyard;

import java.util.logging.Logger;
import java.util.List;

import net.d3mok.uruk.scrapyard.Scrapyard;
import net.d3mok.uruk.plugins.NUCommandExecutor;
import net.d3mok.uruk.utils.NULocations;

import org.bukkit.ChatColor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.entity.Minecart;

public class ScrapyardCommandExecutor extends NUCommandExecutor {

    private Scrapyard plugin;
    private Logger logger;
 
    public ScrapyardCommandExecutor(Scrapyard plugin) {
        super(plugin);
        this.plugin = plugin;
        this.logger = plugin.getLogger();
    }
    
    private void notifyStatus(String status, CommandSender sender) {
        String msg = "";
        String stat = "";
        Boolean iskillable = false;
        
        if (status.equals("minecart")) {
            msg = "Minecarts are currently ";
            iskillable = this.plugin.killableMinecarts;
        } else if (status.equals("boat")) {
            msg = "Boats are currently ";
            iskillable = this.plugin.killableBoats;
        }
        
        if (iskillable) {
            stat = "killable";
        } else {
            stat = "unkillable";
        }
        
        sender.sendMessage(ChatColor.WHITE + "[Scrapyard] " + msg + stat);
    }
    
    private String getType(String s) {
        if (s.matches("^(mine)?carts?")) {
            return "minecart";
        } else if (s.matches("^boats?$")) {
            return "boat";
        } else {
            return "";
        }
    }
    
    private boolean scrap(CommandSender sender, Command cmd, String label, String[] args) {
        
        
        if (args.length < 1 || args.length > 2) {
            return false;
        }
        
        Boolean toggle;
        String type = getType(args[0]);
        
        if (type == "minecart") {
            toggle = !this.plugin.killableMinecarts;
        } else if (type == "boat") {
            type = "boat";
            toggle = !this.plugin.killableBoats;
        } else if (args[0].equals("status")) {
            notifyStatus("minecart", sender);
            notifyStatus("boat", sender);
            return true;
        } else {
            return false;
        }
        
        if (args.length == 2) { 
            switch (args[1]) {
                case "on":
                    toggle = true;
                    break;
                case "off":
                    toggle = false;
                    break;
                case "status":
                    notifyStatus(type, sender);
                    return true;
                default:
                    return false;
            }
        }
        
        if (type.equals("boat")) {
            this.plugin.killableBoats = toggle;
            notifyStatus("boat", sender);
        } else if (type.equals("minecart")) {
            this.plugin.killableMinecarts = toggle;
            notifyStatus("minecart", sender);
        } else {
            return false;
        }
        
        return true;
    }
    
    private boolean junk(CommandSender sender, Command cmd, String label, String[] args) {
        
        Double radius;
        String type = null;
        
        if (args.length < 1 || args.length > 2) {
            return false;
        } else if (args.length == 2) {
            type = getType(args[1]);
        }
        
        radius = Double.valueOf(args[0]);
        
        Entity e = NULocations.getEntity(sender);
        
        if (e == null) {
            sender.sendMessage(ChatColor.RED + "this command can only be used by players or command blocks.");
        }

        List<Entity> nearby = e.getNearbyEntities(radius, radius, radius);
        for (Entity n : nearby) {
            if (type == null || type.equals("minecart")) {
                if (n instanceof Minecart) {
                    n.remove();
                }
            }
            if (type == null || type.equals("boat")) {
                if (n instanceof Boat) {
                    n.remove();
                }
            }
        }
        
        return true;
        
    }
    
    @Override
    public boolean delegateCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        String cmd_name = cmd.getName();
        
        switch (cmd_name) {
             case "scrap":
                 return scrap(sender, cmd, label, args);
             case "junk":
                 return junk(sender, cmd, label, args);
             default:
                 return false;
        }
        
    }
    
}