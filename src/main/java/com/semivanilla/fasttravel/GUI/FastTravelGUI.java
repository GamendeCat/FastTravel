package com.semivanilla.fasttravel.GUI;

import com.semivanilla.fasttravel.FastTravel;
import com.semivanilla.fasttravel.TeleportTask;
import com.semivanilla.fasttravel.Waypoint;
import com.semivanilla.fasttravel.util.ItemBuilder;
import com.semivanilla.fasttravel.util.MiniMessageUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastTravelGUI implements GUI{

    private Inventory inv;
    private FastTravel plugin;

    public FastTravelGUI(FastTravel plugin, Player player) {
        int size = plugin.getConfig().getInt("rows") * 9 + 9;
        inv = Bukkit.createInventory(null, size, Component.text("§8Waypoints"));
        this.plugin = plugin;
        putItems();
        initializeItems(player);
    }

    public void putItems() {
        ItemStack itemStack = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, 1, "§7").build();

        ItemStack home = new ItemBuilder(Material.BARRIER, 1, "§cHome").build();

        ItemStack info = new ItemBuilder(Material.BOOK, 1, "§fInfo").lore(
                Arrays.asList("§7You will be able to", "§7teleport to a waypoint", "§7when you come in", "§7a raduis of " + plugin.getConfig().get("distance") +  ".")).build();


        inv.setItem((inv.getSize() - 4), info);
        inv.setItem((inv.getSize() - 6), home);

        for(int start = (inv.getSize() - 9); start < inv.getSize(); start++) {
            if(inv.getItem(start) != null) continue;
            inv.setItem(start, itemStack);
        }
    }

    public void initializeItems(Player player) {
        for(Waypoint waypoint : plugin.getWaypointManager().getWaypointList()) {
            Location location = waypoint.getLocation();
            String place = Math.round(location.getX()) + " " + Math.round(location.getY()) + " " + Math.round(location.getZ());
            ItemBuilder itemBuilder = (new ItemBuilder(Material.GRAY_DYE, 1, "§c" + waypoint.getName()))
                    .lore(Arrays.asList("§7You have not yet", "§7discovered this waypoint.", "§7Go to " + place));
            if (this.plugin.getPlayerManager().checkWaypoint(player, waypoint.getName())) {
                String loc = "(" + Math.round(location.getX()) + " " + Math.round(location.getY()) + " " + Math.round(location.getZ()) + ")";
                itemBuilder.displayname("§a" + waypoint.getName()).lore(Arrays.asList("§7" + waypoint.getDescription() + "\n" + loc )).material(waypoint.getMaterial());
            }

            this.inv.addItem(itemBuilder.build());
        }
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    @Override
    public String getName() {
        return "§7Waypoints";
    }

    @Override
    public GUI handleClick(Player player, ItemStack itemstack, InventoryView view) {
        if(itemstack == null) return null;
        if(itemstack.getType() == Material.BARRIER) {
            player.closeInventory();
            player.performCommand("home");
            return null;
        }

        for(Waypoint waypoint : plugin.getWaypointManager().getWaypointList()) {
            if(itemstack.getItemMeta().getDisplayName().contains(waypoint.getName())) {
                if(plugin.getPlayerManager().checkWaypoint(player, waypoint.getName())) {
                    MiniMessageUtil.sendMiniMessage(player, plugin.getConfig().getString("delayMessage"), null);
                    TeleportTask teleportTask = new TeleportTask(player, plugin, waypoint);
                    teleportTask.runTaskTimer(plugin, 0, 20);
                    plugin.getPlayerManager().getPlayerMap().put(player, teleportTask);
                    player.closeInventory();
                    break;
                }
            }
        }
        return null;
    }

    @Override
    public boolean isInventory(InventoryView view) {
        return view.title().equals(getName());
    }
}

