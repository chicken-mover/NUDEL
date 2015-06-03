package net.d3mok.uruk.huwawa;

import net.d3mok.uruk.huwawa.HuwawaListener;
import net.d3mok.uruk.plugins.NUPlugin;

import org.bukkit.event.HandlerList;

public class Huwawa extends NUPlugin {

    private HuwawaListener listener;
    
    @Override
    public void onEnable() {
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