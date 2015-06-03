package net.d3mok.uruk.jehova;

import net.d3mok.uruk.jehova.JehovaCommandExecutor;
import net.d3mok.uruk.jehova.JehovasWitness;

import net.d3mok.uruk.plugins.NUPlugin;

import org.bukkit.event.HandlerList;


public class Jehova extends NUPlugin {
    
    private JehovasWitness witness;

    @Override
    public void onEnable() {
        
        // Register command handler for creating lightning strikes
        JehovaCommandExecutor exec = new JehovaCommandExecutor(this);
        getCommand("strike").setExecutor(exec);
        getCommand("boom").setExecutor(exec);
        getCommand("storm").setExecutor(exec);
        
        // Register event listener for handling thunder noise
        witness = new JehovasWitness();
        getServer().getPluginManager().registerEvents(witness, this);
        
    	getLogger().info("Jehova's Lightning Tools are now enabled (apotheosis into the clouds; Kennington via Charing Cross).");
    }
   
    @Override
    public void onDisable() {
        HandlerList.unregisterAll(witness);
    	getLogger().info("Jehova's Lightning Tools are now disbaled (apoandrosis is a bitch).");
    }
    
}
