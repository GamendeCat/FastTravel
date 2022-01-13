package com.semivanilla.fasttravel.commands;

import com.semivanilla.fasttravel.GUI.FastTravelGUI;
import com.semivanilla.fasttravel.util.MiniMessageUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.Template;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastTravel implements CommandExecutor, TabCompleter {

    public static List<String> commands = new ArrayList<>();
    private com.semivanilla.fasttravel.FastTravel plugin;

    public FastTravel(com.semivanilla.fasttravel.FastTravel plugin) {
        this.plugin = plugin;
        SubCommand.loadCommands(plugin);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if(args.length == 0) {
            plugin.getGui().setGUI(player, new FastTravelGUI(plugin, player));
            return true;
        }

        String command = args[0];
        if (commands.contains(command)) {
            SubCommand subCommand = SubCommand.getCommands().get(command);
            if (subCommand.getPermission() != null) {
                if (!sender.hasPermission(subCommand.getPermission())) {
                    MiniMessageUtil.sendMiniMessage(sender, plugin.getConfig().getString("deniedMessage"), Arrays.asList(Template.template("permission", subCommand.getPermission())));
                    return true;
                }
            }
            subCommand.onCommand(sender, Arrays.copyOfRange(args,1, args.length));
            return true;
        }
        MiniMessageUtil.sendMiniMessage(sender, plugin.getConfig().getString("commandHelp"), null);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], commands, completions);
        }
        Collections.sort(completions);
        return completions;
    }
}
