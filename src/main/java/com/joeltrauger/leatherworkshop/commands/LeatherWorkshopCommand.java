package com.joeltrauger.leatherworkshop.commands;

import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.joeltrauger.leatherworkshop.LeatherWorkshop;
import com.joeltrauger.leatherworkshop.utils.LangFile;

public class LeatherWorkshopCommand implements CommandExecutor {
    private LeatherWorkshop plugin;

    public LeatherWorkshopCommand(LeatherWorkshop plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                // Command for "/leatherworkshop reload" 
                if (sender.hasPermission("leatherworkshop.reload")) {
                    // Send message that the reload is starting
                    sender.sendMessage(plugin.getLangFile().getMessage("reloading-plugin", "&cReloading LeatherWorkshop...", true));
                    
                    // Reload Plugin
                    plugin.ensureConfigExists(); 
                    plugin.ensureLanguageFilesExist();
                    plugin.reloadConfig();
                    
                    // Register Event
                    plugin.onEvents();

                    // Unload and reregister Recipe
                    plugin.unloadRecipe();
                    plugin.onRecipe();
        
                    // Reinitialize LangFile to reload possibly extracted language files (if user deleted them)
                    String language = plugin.getConfig().getString("language", "en");
                    plugin.setLangFile(new LangFile(plugin, language));

                    // Update Language in bStats
                    plugin.updateMetricLanguage();

                    // Send Message that the reload completed
                    sender.sendMessage(plugin.getLangFile().getMessage("reload-completed", "&6Successfully reloaded the Plugin!", true));
                } else {
                    sender.sendMessage(plugin.getLangFile().getMessage("no-permission", "&cYou do not have permission to use this command!", true));
                }
            }
            // Command for "/leatherworkshop language" 
            else if (args[0].equalsIgnoreCase("language")) {
                if (sender.hasPermission("leatherworkshop.language")) {
                    if (args.length == 2) {
                        String requestedLang = args[1];
                        if (plugin.doesLanguageExist(requestedLang)) {
                            // ensure config and language file exist
                            plugin.ensureConfigExists(); 
                            plugin.ensureLanguageFilesExist();

                            // Load Configuration from configFile and set the language
                            FileConfiguration Config = YamlConfiguration.loadConfiguration(plugin.ConfigFile);
                            Config.set("language", requestedLang);

                            // Save the updated configuration
                            try {
                                Config.save(plugin.ConfigFile);
                            } catch (IOException e) {
                                e.printStackTrace(); // Log an error if something goes wrong
                            }

                            // Reload configuration
                            plugin.reloadConfig();

                            // Reinitialize LangFile to reload language
                            String language = plugin.getConfig().getString("language", requestedLang);
                            plugin.setLangFile(new LangFile(plugin, language));

                            // Update Language in bStats
                            plugin.updateMetricLanguage();

                            // Give atleast some feedback
                            sender.sendMessage(plugin.getLangFile().getMessage("language-set", "&6Successfully set the language file.", true));
                        } else {
                            // Language does not exist
                            sender.sendMessage(plugin.getLangFile().getMessage("language-not-exists", "&4The provided language does not exist in the \"lang\" folder of the plugin.", true));
                        }
                    }
                    else {
                        sender.sendMessage(plugin.getLangFile().getMessage("language-usage", "&4Usage: /<command> language <language>", true));
                    }
                }
                else {
                    sender.sendMessage(plugin.getLangFile().getMessage("no-permission", "&cYou do not have permission to use this command!", true));
                }
            }
            // Command for "/leatherworkshop help" 
            else if (args[0].equalsIgnoreCase("help")) { 
                if (sender.hasPermission("leatherworkshop.help")) {
                    for (String helpMessage : plugin.getLangFile().getMessageList("leatherworkshop-help")) {
                        sender.sendMessage(helpMessage);
                    }
                } else {
                    sender.sendMessage(plugin.getLangFile().getMessage("no-permission", "&cYou do not have permission to use this command!", true));
                }
            }
            // Command for "/leatherworkshop info"
            else if (args[0].equalsIgnoreCase("info")) { 
                if (sender.hasPermission("leatherworkshop.info")) {
                    sender.sendMessage(plugin.getLangFile().getMessage("plugin-info", "&5Plugin made by traugdor, modified and updated by Holt. See \"/leatherworkshop help\" for a list of subcommands.", true));
                }
                else {
                    sender.sendMessage(plugin.getLangFile().getMessage("no-permission", "&cYou do not have permission to use this command!", true));
                }
            }
            // Text if command was used wrong 
            else {
                sender.sendMessage(plugin.getLangFile().getMessage("improper-usage", "&cImproper usage! See \"/leatherworkshop help\" for a list of subcommands.", true));
            }
        }
        else {
            sender.sendMessage(plugin.getLangFile().getMessage("plugin-info", "&5Plugin made by traugdor, modified and updated by Holt. See \"/leatherworkshop help\" for a list of subcommands.", true));
        }
        return true;
    }
}
