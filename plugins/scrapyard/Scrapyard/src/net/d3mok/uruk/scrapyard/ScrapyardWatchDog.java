package net.d3mok.uruk.scrapyard;

import net.d3mok.uruk.scrapyard.Scrapyard;

import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ScrapyardWatchDog implements Listener {
    
    private Scrapyard plugin;
    
    public ScrapyardWatchDog(Scrapyard plugin) {
        super();
        this.plugin = plugin;
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!e.isCancelled()) {
            Entity damager = e.getDamager();
            Entity damaged = e.getEntity();
            
            if (damager instanceof Projectile) {
                if (damaged instanceof Boat && this.plugin.killableBoats) {
                    e.setCancelled(true);
                } else if (damaged instanceof Minecart && this.plugin.killableMinecarts) {
                    e.setCancelled(true);
                }
            }
            
        }
    }
    
}