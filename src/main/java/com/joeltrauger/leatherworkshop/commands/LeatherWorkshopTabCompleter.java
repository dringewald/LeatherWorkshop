package com.joeltrauger.leatherworkshop.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.joeltrauger.leatherworkshop.LeatherWorkshop;

public class LeatherWorkshopTabCompleter implements TabCompleter {
    private LeatherWorkshop plugin;

    public LeatherWorkshopTabCompleter(LeatherWorkshop plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        
        if (args.length == 1) {
            List<String> subcommands = Arrays.asList("reload", "language", "help", "info");
            for (String subcommand : subcommands) {
                if (subcommand.startsWith(args[0].toLowerCase())) {
                    completions.add(subcommand);
                }
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("language")) {
            // Using the method in the plugin class to get the list of available languages
            List<String> languages = plugin.getAvailableLanguages();
            for (String lang : languages) {
                if (lang.startsWith(args[1].toLowerCase())) {
                    completions.add(lang);
                    completions.add("<custom_language>");
                }
            }
        }
        
        return completions;
    }
}