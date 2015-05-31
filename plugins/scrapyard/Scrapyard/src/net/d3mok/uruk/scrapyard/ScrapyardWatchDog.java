package net.d3mok.uruk.scrapyard;

import net.d3mok.uruk.scrapyard.Scrapyard;

import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDamageEvent;

public class ScrapyardWatchDog implements Listener {
    
    private Scrapyard plugin;
    
    public ScrapyardWatchDog(Scrapyard plugin) {
        super();
        this.plugin = plugin;
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onDamage(VehicleDamageEvent e) {
        if (!e.isCancelled() && (e.getAttacker() instanceof Player)) {
            if (e.getVehicle() instanceof Boat && !this.plugin.killableBoats) {
                e.setCancelled(true);
            } else if (e.getVehicle() instanceof Minecart && !this.plugin.killableMinecarts) {
                e.setCancelled(true);
            }
        }
    }
    
}