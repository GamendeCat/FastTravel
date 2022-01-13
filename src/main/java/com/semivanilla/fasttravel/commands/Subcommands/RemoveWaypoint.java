package com.semivanilla.fasttravel.commands.Subcommands;

import com.semivanilla.fasttravel.FastTravel;
import com.semivanilla.fasttravel.commands.SubCommand;
import com.semivanilla.fasttravel.util.MiniMessageUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveWaypoint extends SubCommand {

    private FastTravel plugin;

    public RemoveWaypoint(FastTravel plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getSub() {
        return "remove";
    }

    @Override
    public String getPermission() {
        return "fasttravel.remove";
    }

    @Override
    public String getDescription() {
        return "Remove a waypoint";
    }

    @Override
    public String getSyntax() {
        return "usage: /fasttravel remove <waypoint>";
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        try {
            plugin.getWaypointManager().removingWaypoint(args[1]);
            MiniMessageUtil.sendMiniMessage(player, plugin.getConfig().getString("waypointRemoveMessage"), null);
        }catch(ArrayIndexOutOfBoundsException e) {
            MiniMessageUtil.sendMiniMessage(sender, getSyntax(), null);
        }
    }
}
