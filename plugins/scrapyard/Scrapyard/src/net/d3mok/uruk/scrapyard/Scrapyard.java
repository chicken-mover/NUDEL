package net.d3mok.uruk.scrapyard;

import net.d3mok.uruk.scrapyard.ScrapyardCommandExecutor;
import net.d3mok.uruk.scrapyard.ScrapyardWatchDog;
import net.d3mok.uruk.plugins.NUPlugin;

import org.bukkit.event.HandlerList;

public class Scrapyard extends NUPlugin {

    private ScrapyardWatchDog watchdog;
    
    public boolean killableMinecarts = true;
    public boolean killableBoats = true;
    
    @Override
    public void onEnable() {
        
        ScrapyardCommandExecutor exec = new ScrapyardCommandExecutor(this);
        getCommand("scrap").setExecutor(exec);
        getCommand("junk").setExecutor(exec);
        
        watchdog = new ScrapyardWatchDog(this);
        getServer().getPluginManager().registerEvents(watchdog, this);
        
        getLogger().info("Scrapyard enabled (this is the art police; step away from the skip).");
        
    }
   
    @Override
    public void onDisable() {
        HandlerList.unregisterAll(watchdog);
        getLogger().info("Scrapyard disabled (you get your hair cut).");
    }
    
}