package com.semivanilla.fasttravel.commands.Subcommands;

import com.semivanilla.fasttravel.FastTravel;
import com.semivanilla.fasttravel.commands.SubCommand;
import com.semivanilla.fasttravel.util.MiniMessageUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.Template;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Help extends SubCommand {

    private FastTravel plugin;

    public Help(FastTravel plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getSub() {
        return "help";
    }

    @Override
    public String getPermission() {
        return "fasttravel.help";
    }

    @Override
    public String getDescription() {
        return "List all subcommands and it's descriptions";
    }

    @Override
    public String getSyntax() {
        return "usage: /fasttravel help";
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        final Component[] message = {MiniMessageUtil.parseMiniMessage("<aqua>FastTravel Commands: ", null)};
        SubCommand.getCommands().forEach((sub, command) -> {
            if (command != null) {
                if (sender.hasPermission(command.getPermission())) {
                    message[0] = message[0].append(Component.newline());
                    String commandHelp = plugin.getConfig().getString("commandHelpList")
                            .replace("<command>", command.getSub())
                            .replace("<permission>", command.getPermission())
                            .replace("<description>", command.getDescription())
                            .replace("<usage>", command.getSyntax());
                    Component info = MiniMessageUtil.parseMiniMessage("<gray>" + commandHelp,null);
                    message[0] = message[0].append(info);
                }
            }
        });
        sender.sendMessage(message[0]);
    }
}
