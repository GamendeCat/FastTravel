package com.semivanilla.fasttravel;

import com.semivanilla.fasttravel.events.PlayerMoveListener;
import com.semivanilla.fasttravel.events.PlayerSneakListener;
import com.semivanilla.fasttravel.util.MiniMessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportTask extends BukkitRunnable {

    private Player player;
    private FastTravel plugin;
    private Waypoint waypoint;
    private PlayerMoveListener event;

    public TeleportTask(Player player, FastTravel plugin, Waypoint waypoint) {
        this.player = player;
        this.plugin = plugin;
        this.waypoint = waypoint;
        this.event = new PlayerMoveListener(plugin);
        plugin.getServer().getPluginManager().registerEvents(event, plugin);
    }

    private int currentSecond = 0;

    @Override
    public void run() {
        currentSecond++;

        if(currentSecond == 5) {
            plugin.getPlayerManager().getPlayerMap().remove(player);
            player.teleport(waypoint.getLocation());
            MiniMessageUtil.sendMiniMessage(player, plugin.getConfig().getString("teleportMessage"), null);
            cancel();
        }
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        PlayerMoveEvent.getHandlerList().unregister(event);
        EntityDamageEvent.getHandlerList().unregister(event);
        EntityDamageByEntityEvent.getHandlerList().unregister(event);
        super.cancel();
    }
}
