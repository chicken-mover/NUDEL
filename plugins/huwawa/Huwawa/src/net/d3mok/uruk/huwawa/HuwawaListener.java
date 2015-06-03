package net.d3mok.uruk.huwawa;

import net.d3mok.uruk.huwawa.Huwawa;
import net.d3mok.uruk.utils.NULocations;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;

import com.sk89q.worldedit.LocalConfiguration;


public class HuwawaListener implements Listener {
    
    private Huwawa plugin;
    
    public HuwawaListener(Huwawa plugin) {
        super();
        this.plugin = plugin;
    }

    private boolean locationInFlaggedRegions(Location loc) {
        ApplicableRegionSet set = NULocations.locationInRegions(loc, this.plugin.getWorldGuard());
        if (set == null) {
            return false;
        }
        GameMode flag = set.queryValue(null, DefaultFlag.GAME_MODE);
        if (flag == null) {
            return false;
        }
        // We can just test the entire set here, we don't have to iterate them
        switch(flag) {
            case ADVENTURE:
            case SURVIVAL:
                return true;
            case CREATIVE:
            case SPECTATOR:
            default:
                return false;
        }
    }

    private boolean playerInFlaggedRegions(Player player) {
        return locationInFlaggedRegions(player.getLocation());
    }

    private String getCommand(String msg) {
        msg = msg.replaceAll("^/", "");
        String[] parts = msg.split(" ");
        return parts[0];
    }

    private void removeWandFromInventory(Player player) {
        LocalConfiguration conf = this.plugin.getWorldEdit().getLocalConfiguration();
        // 6.x SHOULD provide a non-int way of doing this, but I can't find docs
        // on it. This is deprecated, anyway.
        int id = conf.wandItem;
        ItemStack wandItem = new ItemStack(id);
        PlayerInventory inv = player.getInventory();
        inv.remove(wandItem);
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onMove(PlayerMoveEvent e) {
        ApplicableRegionSet set = NULocations.playerInRegions(e.getPlayer(), this.plugin.getWorldGuard());
        Player player = e.getPlayer();
        // Enforce Game Mode restrictions, over and above WorldEdit's handling.
        GameMode mode = set.queryValue(null, DefaultFlag.GAME_MODE);
        if (mode != null && !(player.getGameMode().equals(mode))) {
            player.setGameMode(mode);
            this.plugin.notify(player, "You are now in a "+String.valueOf(mode)+" region. Your game mode has been set to match.");
            removeWandFromInventory(player);
        }
    }

    // Block commands by players in survival regions
    @EventHandler (priority = EventPriority.HIGH)
    public void onPreprocessCommand(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        String command = getCommand(e.getMessage());
        if (playerInFlaggedRegions(player)) {
            // All WorldEdit commands start with a double slash
            if (command.startsWith("/")) {
                e.setCancelled(true);
            }
            // This is just a grab-bag of anything I can think of that is cheating
            // in a survival zone.
            switch(command) {
                case "give":
                case "tp":
                case "gamemode":
                case "clear":
                case "kill":
                case "heal":
                case "butcher":
                case "home":
                case "difficulty":
                case "time":
                case "scoreboard":
                // WorldGuard
                case "region":
                case "rg":
                    e.setCancelled(true);
                    this.plugin.notify(player, "The command '"+command+"' has been blocked, because you are in a survival or adventure zone.");
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        Location from = e.getFrom();
        Location dest = e.getTo();
        Player player = e.getPlayer();
        if (NULocations.closeEnough(from, dest)) {
            // Could be a login event, or some other trivial teleport. Doesn't
            // matter enough for us to catch it.
            return;
        }
        // For some reason this isn't always set correctly in Spigot, so is
        // actually quite unreliable.
        PlayerTeleportEvent.TeleportCause cause = e.getCause();

        if (cause != null && (
            cause.equals(PlayerTeleportEvent.TeleportCause.COMMAND) || 
            cause.equals(PlayerTeleportEvent.TeleportCause.PLUGIN)  ||
            // Ideally we wouldn't check UNKNOWNS, but Spigot seems to set some
            // events as unknown that are clearly commands.
            cause.equals(PlayerTeleportEvent.TeleportCause.UNKNOWN)
           )) {
            if (locationInFlaggedRegions(dest)) {
                this.plugin.notify(player, "The target teleport location is in a survival or adventure zone, so you can't teleport there.");
                e.setCancelled(true);
            }
        }

    }    
}