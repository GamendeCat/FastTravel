package com.semivanilla.fasttravel.managers;

import com.semivanilla.fasttravel.FastTravel;
import com.semivanilla.fasttravel.Waypoint;
import com.semivanilla.fasttravel.util.ConfigurationFile;
import com.semivanilla.fasttravel.util.LocationUtility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class WaypointManager {

    private List<Waypoint> waypointList = new ArrayList<>();
    private ConfigurationFile config;
    private ConfigurationSection waypointSection;

    public WaypointManager(FastTravel plugin) {
        config = new ConfigurationFile(plugin, "data/waypoints.yml");

        if(config.getCustomConfig().getConfigurationSection("waypoints") != null) {
            waypointSection = config.getCustomConfig().getConfigurationSection("waypoints");
        }else {
            waypointSection = config.getCustomConfig().createSection("waypoints");
        }

        readingWaypoints(waypointSection);
    }

    public void readingWaypoints(ConfigurationSection waypoints) {
        for(String key : waypoints.getKeys(false)) {
            ConfigurationSection section = waypoints.getConfigurationSection(key);
            Location location = LocationUtility.read(section.getConfigurationSection("location"));
            Material material = Material.getMaterial(section.getString("material"));
            waypointList.add(new Waypoint(location, material, key, section.getString("description")));
        }
    }

    public void addingWaypoint(Location location, Material material, String name, String description) {
        ConfigurationSection section = waypointSection.createSection(name);
        LocationUtility.write(location, section.createSection("location"));
        section.set("material", material.name());
        section.set("description", description);
        waypointList.add(new Waypoint(location, material, name, description));
        config.saveConfig();
    }

    public boolean isInList(String waypointName) {
        for(Waypoint waypoint : waypointList) {
            if(waypoint.getName().equalsIgnoreCase(waypointName)) {
                return true;
            }
        }
        return false;
    }

    public void removingWaypoint(String waypointName) {
        for(Waypoint waypoint : waypointList) {
            if(waypoint.getName().equalsIgnoreCase(waypointName)) {
                waypointList.remove(waypoint);
                waypointSection.set(waypointName, null);
                config.saveConfig();
            }
        }
    }

    public List<Waypoint> getWaypointList() {
        return waypointList;
    }
}
