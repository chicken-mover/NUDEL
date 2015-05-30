package net.d3mok.uruk.jehova;

import net.d3mok.uruk.jehova.JehovaEntityLightning;

import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityWeather;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.LightningStrikeEvent;

public class JehovasWitness implements Listener {

    @EventHandler (priority = EventPriority.HIGH)
    public void onLightningStrike(LightningStrikeEvent e) {
        if (!e.isCancelled() && e.getLightning().isEffect()) {
            e.setCancelled(true);
        } else {
            return;
        }
        
        LightningStrike original_bolt = e.getLightning();
        
        Double x = original_bolt.getLocation().getX();
        Double y = original_bolt.getLocation().getY();
        Double z = original_bolt.getLocation().getZ();
        
        JehovaEntityLightning el = new JehovaEntityLightning(((CraftWorld)e.getLightning().getLocation().getWorld()).getHandle(), x, y, z);
        
        PacketPlayOutSpawnEntityWeather packet = new PacketPlayOutSpawnEntityWeather(el);
        World w = original_bolt.getLocation().getWorld();
        
        for (Player p : w.getPlayers()) {
            
            Location pl = p.getLocation();
            Double px = pl.getX();
            Double py = pl.getY();
            
            Double delta_x = Math.abs(px - x);
            Double delta_y = Math.abs(py - y);
            
            if (delta_x < 200 && delta_y < 200) {
                ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
                ((Player) p).playSound(p.getLocation(), Sound.AMBIENCE_THUNDER, 10000.0F, 0.8F + (float)Math.round(Math.random() * 0.2D));
                ((Player) p).playSound(original_bolt.getLocation(), Sound.EXPLODE, 2.0F, 0.5F + (float)Math.round(Math.random() * 0.2D));
            }
            
        }

    }
    
}