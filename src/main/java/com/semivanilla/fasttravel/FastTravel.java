package com.semivanilla.fasttravel;

import com.semivanilla.fasttravel.GUI.GUIManager;
import com.semivanilla.fasttravel.events.InventoryClickListener;
import com.semivanilla.fasttravel.events.PlayerSneakListener;
import com.semivanilla.fasttravel.managers.PlayerManager;
import com.semivanilla.fasttravel.managers.WaypointManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class FastTravel extends JavaPlugin {

    private WaypointManager waypointManager;
    private PlayerManager playerManager;
    private GUIManager gui;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getLogger().info("FastTravel Started up!");
        waypointManager = new WaypointManager(this);
        playerManager = new PlayerManager(this);
        gui = new GUIManager();

        getServer().getPluginManager().registerEvents(new PlayerSneakListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
        getCommand("fasttravel").setExecutor(new com.semivanilla.fasttravel.commands.FastTravel(this));
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public WaypointManager getWaypointManager() {
        return waypointManager;
    }

    public GUIManager getGui() {
        return gui;
    }
}
