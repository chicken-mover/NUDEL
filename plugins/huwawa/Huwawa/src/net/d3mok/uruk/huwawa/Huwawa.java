package net.d3mok.uruk.huwawa;

import net.d3mok.uruk.huwawa.HuwawaListener;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class Huwawa extends JavaPlugin {

    private HuwawaListener listener;
    
    @Override
    public void onEnable() {
        
        // Register event listener for handling thunder noise
        listener = new HuwawaListener(this);
        getServer().getPluginManager().registerEvents(listener, this);
        getLogger().info("Huwawa enabled (where names are set up I will set up my name).");
        
    }
   
    @Override
    public void onDisable() {
        HandlerList.unregisterAll(listener);
        getLogger().info("Huwawa disabled (where no names are set up, I will set up gods' names.");
    }
    
}