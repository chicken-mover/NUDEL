package net.d3mok.uruk.huwawa;

import net.d3mok.uruk.huwawa.Huwawa;

import java.lang.RuntimeException;

import org.bukkit.GameMode;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.LocalConfiguration;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

//import static com.sk89q.worldedit.bukkit.BukkitUtil.*;


public class HuwawaListener implements Listener {
    
    private Huwawa plugin;
    private PluginManager manager;
    
    public HuwawaListener(Huwawa plugin) {
        super();
        this.plugin = plugin;
        this.manager = this.plugin.getServer().getPluginManager();
    }
    
    private WorldGuardPlugin getWorldGuard() {
        Plugin w = manager.getPlugin("WorldGuard");
        if (w == null) {
            throw new RuntimeException("[Huwawa] Can't load WorldGuard!");
        }
        return (WorldGuardPlugin) w;
    }
    
    private WorldEditPlugin getWorldEdit() {
        Plugin w = manager.getPlugin("WorldEdit");
        if (w == null) {
            throw new RuntimeException("[Huwawa] Can't load WorldEdit!");
        }
        return (WorldEditPlugin) w;
    }

    private ApplicableRegionSet locationInRegions(Location loc) {
        World world = loc.getWorld();
        RegionContainer container = getWorldGuard().getRegionContainer();

        Vector pt = BukkitUtil.toVector(loc);
        RegionManager manager = container.get(world);
        ApplicableRegionSet regions = manager.getApplicableRegions(pt);

        return regions;
    }

    private ApplicableRegionSet playerInRegions(Player player) {
        Location loc = player.getLocation();
        return locationInRegions(loc);
    }

    private boolean locationInFlaggedRegions(Location loc) {
        ApplicableRegionSet set = locationInRegions(loc);
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

    private void notify(Player player, String cmd) {
        String msg = "[Huwawa] The command '"+cmd+"' has been blocked, because you are in a survival or adventure zone.";
        player.sendMessage(ChatColor.RED+msg);
    }

    private void removeWandFromInventory(Player player) {
        LocalConfiguration conf = getWorldEdit().getLocalConfiguration();
        // 6.x SHOULD provide a non-int way of doing this, but I can't find docs
        // on it. This is deprecated, anyway.
        int id = conf.wandItem;
        ItemStack wandItem = new ItemStack(id);

        PlayerInventory inv = player.getInventory();
        inv.remove(wandItem);
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onMove(PlayerMoveEvent e) {
        ApplicableRegionSet set = playerInRegions(e.getPlayer());
        Player player = e.getPlayer();
        // Enforce Game Mode restrictions, over and above WorldEdit's handling.
        GameMode mode = set.queryValue(null, DefaultFlag.GAME_MODE);
        if (mode != null) {
            player.setGameMode(mode);
            player.sendMessage(ChatColor.WHITE+"[Huwawa] Game mode set: "+String.valueOf(mode)+" (now: "+String.valueOf(player.getGameMode())+")");
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
                    notify(player, command);
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        Location dest = e.getTo();
        PlayerTeleportEvent.TeleportCause cause = e.getCause();
        this.plugin.getLogger().info("Teleport detected: "+String.valueOf(cause)+" "+String.valueOf(e.getFrom())+" -> "+String.valueOf(e.getTo()));
        if (cause != null && (
            cause.equals(PlayerTeleportEvent.TeleportCause.COMMAND) || 
            cause.equals(PlayerTeleportEvent.TeleportCause.PLUGIN)  
           )) {
            if (locationInFlaggedRegions(dest)) {
                this.plugin.getLogger().info("Teleport into exclusion zone detected: "+String.valueOf(cause));
                e.setCancelled(true);
            }
        }
    }    
}