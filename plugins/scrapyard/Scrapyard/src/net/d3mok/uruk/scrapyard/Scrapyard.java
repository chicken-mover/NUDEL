package net.d3mok.uruk.scrapyard;

import net.d3mok.uruk.scrapyard.ScrapyardCommandExecutor;
import net.d3mok.uruk.scrapyard.ScrapyardWatchDog;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class Scrapyard extends JavaPlugin {

    private ScrapyardWatchDog watchdog;
    
    public boolean killableMinecarts = true;
    public boolean killableBoats = true;
    
    @Override
    public void onEnable() {
        
        // Register command handler for creating lightning strikes
        ScrapyardCommandExecutor exec = new ScrapyardCommandExecutor(this);
        getCommand("scrap").setExecutor(exec);
        getCommand("junk").setExecutor(exec);
        
        // Register event listener for handling thunder noise
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