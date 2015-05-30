package net.d3mok.uruk.scrapyard;

import java.util.logging.Logger;

import net.d3mok.uruk.scrapyard.Scrapyard;
import net.d3mok.uruk.plugins.NUCommandExecutor;

import org.bukkit.ChatColor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ScrapyardCommandExecutor extends NUCommandExecutor {

    private Scrapyard plugin;
 
    public ScrapyardCommandExecutor(Scrapyard plugin) {
        super(plugin);
    }
    
    private void notifyStatus(String status, CommandSender sender) {
        String msg = "";
        String stat = "";
        Boolean iskillable = false;
        
        if (status.equals("minecart")) {
            msg = "Minecarts are currently ";
            iskillable = this.plugin.killableMinecarts;
        } else if (status.equals("boats")) {
            msg = "Boats are currently ";
            iskillable = this.plugin.killableBoats;
        }
        
        if (iskillable) {
            stat = "killable";
        } else {
            stat = "unkillable";
        }
        
        sender.sendMessage(ChatColor.RED + msg + stat);
    }
    
    @Override
    public boolean delegateCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        Boolean toggle;
        String type = "minecart";
        
        if (!cmd.getName().equals("scrap") || args.length < 1 || args.length > 2) {
            return false;
        }
        
        if (args[0].matches("^minecarts?$")) {
            type = "minecart";
            toggle = !this.plugin.killableMinecarts;
        } else if (args[0].matches("^boats?$")) {
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
        } else if (type.equals("minecart")) {
            this.plugin.killableMinecarts = toggle;
        } else {
            return false;
        }
        
        return true;
    }
    
}