package net.d3mok.uruk.jehova;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.LightningStrikeEvent;

public class JehovasWitness implements Listener {

    @EventHandler (priority = EventPriority.HIGH)
    public void onLightningStrike(LightningStrikeEvent e) {
        // TODO: Catch default events and handle them somehow.
    }
    
}
