package com.semivanilla.fasttravel.events;

import com.semivanilla.fasttravel.FastTravel;
import com.semivanilla.fasttravel.util.MiniMessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    private FastTravel plugin;

    public PlayerMoveListener(FastTravel plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerMoving(PlayerMoveEvent e) {
        System.out.println("why");
        cancelling(e.getPlayer());
    }

    @EventHandler
    public void entityDamageEvent(EntityDamageEvent e) {
        if(!(e.getEntity() instanceof Player)) return;
        System.out.println("why");
        cancelling((Player) e.getEntity());
    }

    @EventHandler
    public void entityDamageEventByEntity(EntityDamageByEntityEvent e) {
        if(!(e.getEntity() instanceof Player)) return;
        System.out.println("why");
        cancelling((Player) e.getEntity());
    }

    public void cancelling(Player playerOne) {
        for(Player player : plugin.getPlayerManager().getPlayerMap().keySet()) {
            if(player == playerOne) {
                plugin.getPlayerManager().getPlayerMap().get(player).cancel();
                MiniMessageUtil.sendMiniMessage(player, plugin.getConfig().getString("requestCancelledMessage"), null);
            }
        }
    }
}
