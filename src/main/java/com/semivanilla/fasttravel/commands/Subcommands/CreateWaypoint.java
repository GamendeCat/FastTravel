package com.semivanilla.fasttravel.commands.Subcommands;

import com.semivanilla.fasttravel.FastTravel;
import com.semivanilla.fasttravel.commands.SubCommand;
import com.semivanilla.fasttravel.util.MiniMessageUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateWaypoint extends SubCommand {

    private FastTravel plugin;

    public CreateWaypoint(FastTravel plugin) {
     this.plugin = plugin;
    }

    @Override
    public String getSub() {
        return "create";
    }

    @Override
    public String getPermission() {
        return "fasttravel.create";
    }

    @Override
    public String getDescription() {
        return "Create a waypoint";
    }

    @Override
    public String getSyntax() {
        return "usage: /fasttravel create <waypoint> <description>";
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        StringBuilder str = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            str.append(args[i] + " ");
        }

        try {
            if(plugin.getWaypointManager().isInList(args[0])) {
              MiniMessageUtil.sendMiniMessage(player,plugin.getConfig().getString("waypointAlreadyExists"), null);
            }

            plugin.getWaypointManager().addingWaypoint(player.getLocation(), player.getInventory().getItemInMainHand().getType(), args[0], str.toString());
            MiniMessageUtil.sendMiniMessage(player, plugin.getConfig().getString("waypointCreateMessage"), null);
        }catch(ArrayIndexOutOfBoundsException e) {
            MiniMessageUtil.sendMiniMessage(sender, getSyntax(), null);
        }
    }

}
