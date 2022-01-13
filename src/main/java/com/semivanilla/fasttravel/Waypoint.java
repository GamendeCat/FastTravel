package com.semivanilla.fasttravel;

import org.bukkit.Location;
import org.bukkit.Material;

public class Waypoint {

    private Location location;
    private Material material;
    private String name;
    private String description;

    public Waypoint(Location location, Material material, String name, String description) {
        this.location = location;
        this.material = material;
        this.name = name;
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }


}
