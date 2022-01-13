package com.semivanilla.fasttravel.commands;

import com.semivanilla.fasttravel.commands.Subcommands.CreateWaypoint;
import com.semivanilla.fasttravel.commands.Subcommands.Help;
import com.semivanilla.fasttravel.commands.Subcommands.RemoveWaypoint;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public abstract class SubCommand {

    private static HashMap<String, SubCommand> commands;

    public abstract String getSub();
    public abstract String getPermission();
    public abstract String getDescription();
    public abstract String getSyntax();

    public abstract void onCommand(CommandSender sender, String[] args);

    public static void loadCommands(com.semivanilla.fasttravel.FastTravel fastTravel) {
        commands = new HashMap<>();

        loadCommand(new CreateWaypoint(fastTravel), new RemoveWaypoint(fastTravel), new Help(fastTravel));
    }

    private static void loadCommand(SubCommand ... subs) {
        for (SubCommand sub: subs) {
            commands.put(sub.getSub(), sub);
            FastTravel.commands.add(sub.getSub());
        }
    }

    public static HashMap<String, SubCommand> getCommands() {
        return commands;
    }
}
