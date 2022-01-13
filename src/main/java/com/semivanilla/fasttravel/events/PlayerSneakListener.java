package com.semivanilla.fasttravel.events;

import com.semivanilla.fasttravel.FastTravel;
import com.semivanilla.fasttravel.Waypoint;
import com.semivanilla.fasttravel.util.MiniMessageUtil;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PlayerSneakListener implements Listener {

    private FastTravel plugin;
    private double distance;

    public PlayerSneakListener(FastTravel plugin) {
        this.plugin = plugin;
        this.distance = plugin.getConfig().getDouble("distance");
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        Player player = e.getPlayer();

        if(e.getPlayer().isSneaking()) return;

        for(Waypoint waypoint : plugin.getWaypointManager().getWaypointList()) {
            if(isPlayerClose(player.getLocation(), waypoint.getLocation())) {
                if(!plugin.getPlayerManager().checkWaypoint(player, waypoint.getName())) {
                    plugin.getPlayerManager().addPlayer(waypoint.getName(), player);
                    MiniMessageUtil.sendMiniMessage(player, plugin.getConfig().getString("playerAddedWaypoint").replaceAll("<waypoint>", waypoint.getName()), null);
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                }
            }
        }
    }

    public boolean isPlayerClose(Location playerLocation, Location waypointLocation) {
        double amount = playerLocation.distance(waypointLocation);

        if(amount < distance) return true;
        else return false;
    }
}
