package com.semivanilla.fasttravel.managers;

import com.semivanilla.fasttravel.FastTravel;
import com.semivanilla.fasttravel.TeleportTask;
import com.semivanilla.fasttravel.util.ConfigurationFile;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerManager {

    private FastTravel plugin;
    private Map<String, List<String>> map = new HashMap<>();
    private FileConfiguration config;
    private ConfigurationFile file;
    private ConfigurationSection section;
    private Map<Player, TeleportTask> playerMap = new HashMap<>();

    public PlayerManager(FastTravel plugin) {
        file = new ConfigurationFile(plugin, "data/playerData.yml");
        config = file.getCustomConfig();
        if(config.getConfigurationSection("playerData") != null) {
            section = config.getConfigurationSection("playerData");
        }else{
            section  = config.createSection("playerData");
        }
        this.plugin = plugin;
        loadPlayerData();
    }

    public void loadPlayerData() {
        for(String uuid : section.getKeys(false)) {
            List<String> waypoints = section.getStringList(uuid);
            map.put(uuid, waypoints);
        }
    }

    public boolean checkWaypoint(Player player, String waypointName) {
        if(map.get(String.valueOf(player.getUniqueId())) == null) {
            return false;
        }

        for(String waypoint : map.get(String.valueOf(player.getUniqueId()))) {
            if(waypointName.equalsIgnoreCase(waypoint)) {
                return true;
            }
        }
        return false;
    }

    public void addPlayer(String waypointName, Player player) {
        List<String> list = map.get(player);
        if(list == null) list = new ArrayList<>();
        list.add(waypointName);

        section.set(String.valueOf(player.getUniqueId()), list);

        map.put(String.valueOf(player.getUniqueId()), list);
        file.saveConfig();
    }

    public void removePlayer(String waypointName, Player player) {
        List<String> list = map.get(player);
        if(list == null) list = new ArrayList<>();
        list.remove(waypointName);

        section.set(String.valueOf(player.getUniqueId()), list);

        map.put(String.valueOf(player.getUniqueId()), list);
        file.saveConfig();
    }

    public Map<Player, TeleportTask> getPlayerMap() {
        return playerMap;
    }
}
