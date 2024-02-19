package com.joeltrauger.leatherworkshop;

import com.joeltrauger.leatherworkshop.commands.LeatherWorkshopCommand;
import com.joeltrauger.leatherworkshop.events.Event_ZombieSmelt;
import com.joeltrauger.leatherworkshop.utils.LangFile;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bstats.bukkit.Metrics;

public final class LeatherWorkshop extends JavaPlugin {
    public static LeatherWorkshop plugin;
    private LangFile langFile;
    private NamespacedKey zombieLeatherKey;
    public File ConfigFile = new File(getDataFolder(), "config.yml");

    public void onEnable() {
        plugin = this;

        int pluginId = 21052; // <-- Replace with the id of your plugin!
        new Metrics(this, pluginId);

        // Ensure Language File exists
        ensureLanguageFilesExist();

        // Load Language
        String language = getConfig().getString("language", "en");
        langFile = new LangFile(this, language);

        // Migrate old config or create new config file to valid yaml
        migrateOldConfig();

        Bukkit.getConsoleSender().sendMessage(plugin.getLangFile().getMessage("enabling-message", "&6Enabling LeatherWorkshop...", true));

        // Register Events
        onEvents();

        // Register Recipe
        onRecipe();
    
        // Register Command
        this.getCommand("leatherworkshop").setExecutor(new LeatherWorkshopCommand(this));

        // Print out Enabled state
        Bukkit.getConsoleSender().sendMessage(plugin.getLangFile().getMessage("finished-enabling", "&6Enabling LeatherWorkshop...", true));
    }

    public void onDisable() {
        // Onload Recipe
        Iterator<Recipe> it = getServer().recipeIterator();
        while (it.hasNext()) {
            Recipe recipe = it.next();
            if (recipe instanceof Keyed) {
                Keyed keyed = (Keyed) recipe;
                if (zombieLeatherKey.equals(keyed.getKey())) {
                    it.remove();
                    Bukkit.getConsoleSender().sendMessage(plugin.getLangFile().getMessage("unloading-recipe", "&cUnloading LeatherWorker Zombie Flesh Recipe...", true));
                }
            }
        }
        Bukkit.getConsoleSender().sendMessage(plugin.getLangFile().getMessage("finished-unloading-recipe", "&6LeatherWorker unloaded the recipe successfully.", true));
        
        // Send Message that the Plugin has been disabled
        Bukkit.getConsoleSender().sendMessage(plugin.getLangFile().getMessage("plugin-disabled", "&6LeatherWorkshop has been disabled.", true));
    }

    public void onEvents() {
        getServer().getPluginManager().registerEvents(new Event_ZombieSmelt(this), this);
        Bukkit.getConsoleSender().sendMessage(plugin.getLangFile().getMessage("registered-furnace", "&cRegistered new furnace event listener.", true));    
    }

    public void onRecipe() {
        Bukkit.getConsoleSender().sendMessage(plugin.getLangFile().getMessage("loading-recipe", "&cLoading LeatherWorker Zombie Flesh Recipe...", true));    
        ItemStack is = new ItemStack(Material.LEATHER, 1);
        zombieLeatherKey = new NamespacedKey((Plugin) plugin, "ZombieLeather");
        int cookSpeed = getConfig().getInt("cook-speed");
        int expGain = getConfig().getInt("xp-gain");
        FurnaceRecipe fr = new FurnaceRecipe(zombieLeatherKey , is, Material.ROTTEN_FLESH, expGain, cookSpeed);
        Bukkit.getServer().addRecipe((Recipe) fr);
        Bukkit.getConsoleSender().sendMessage(plugin.getLangFile().getMessage("finished-recipe", "&6LeatherWorker loaded the recipe successfully.", true));    
    }

    private void migrateOldConfig() {
        File oldConfigFile = new File(getDataFolder(), "LeatherWorkshop.yml");
        
        // Load or create new config
        if (!ConfigFile.exists()) {
            saveResource("config.yml", false);
        }
        FileConfiguration newConfig = YamlConfiguration.loadConfiguration(ConfigFile);

        // If old config exists, migrate settings
        if (oldConfigFile.exists()) {
            Bukkit.getConsoleSender().sendMessage(plugin.getLangFile().getMessage("found-old-config", "&cFound old LeatherWorkshop config file. Migrating to new config.yml...", true));    
            FileConfiguration oldConfig = YamlConfiguration.loadConfiguration(oldConfigFile);

            // Migrate values, using defaults if necessary
            int leatherChance = oldConfig.getInt("Chance of Leather[1-100]", 100);
            int cookSpeed = oldConfig.getInt("Cook speed (default 200)", 200);
            int xpGain = oldConfig.getInt("EXP Gain", 5);

            // Update new config
            newConfig.set("leather-chance", leatherChance);
            newConfig.set("cook-speed", cookSpeed);
            newConfig.set("xp-gain", xpGain);

            try {
                newConfig.save(ConfigFile);
                Bukkit.getConsoleSender().sendMessage(plugin.getLangFile().getMessage("config-migrated", "&6Config migrated successfully.", true));    

                // Delete old config file if migration is successful
                if (oldConfigFile.delete()) {
                    Bukkit.getConsoleSender().sendMessage(plugin.getLangFile().getMessage("delete-old-config", "&6Old config file deleted successfully.", true));
                } else {
                    Bukkit.getConsoleSender().sendMessage(plugin.getLangFile().getMessage("deletion-failed", "&4Failed to delete the old config file. Please delete the File manually.", true));
                }
            } catch (IOException e) {
                Bukkit.getConsoleSender().sendMessage(plugin.getLangFile().getMessage("save-config-failed", "&4Could not save new config file: ", true) + e.getMessage() + ChatColor.RESET);
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(plugin.getLangFile().getMessage("old-config-not-found", "&6No old config found, using default settings.", true));
        }
    }

    // Ensure Language files exists when reloading the plugin 
    public void ensureLanguageFilesExist() {
        File langDir = new File(this.getDataFolder(), "lang");
        if (!langDir.exists()) {
            langDir.mkdirs();
        }
    
        // Check and extract 'en.yml' if it doesn't exist
        File enLangFile = new File(langDir, "en.yml");
        if (!enLangFile.exists()) {
            saveResource("lang/en.yml", false);
        }
    
        // Check and extract 'de.yml' if it doesn't exist
        File deLangFile = new File(langDir, "de.yml");
        if (!deLangFile.exists()) {
            saveResource("lang/de.yml", false);
        }
    }

    // Ensure the config.yml exists when reloading the plugin 
    public void ensureConfigExists() {
        File configFile = new File(this.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }
    }

    // Method to get the LangFile
    public LangFile getLangFile() {
        return langFile;
    }

    // Method to set the LangFile
    public void setLangFile(LangFile langFile) {
        this.langFile = langFile;
    }

    // Check if Language File exists
    public boolean doesLanguageExist(String lang) {
        // Define the directory and the language file
        File langDir = new File(getDataFolder(), "lang");
        File langFile = new File(langDir, lang + ".yml");

        // Check if the language file exists
        if (!langFile.exists()) {
            return false; // Language file does not exist
        }

        // Load the language file
        FileConfiguration langConfig = new YamlConfiguration();
        try {
            langConfig.load(langFile);
        } catch (IOException | InvalidConfigurationException e) {
            // Log the error and return false if the file is malformed or can't be read
            getLogger().severe("Could not load language file " + lang + ".yml: " + e.getMessage());
            return false;
        }

        // Check if all required keys exist in the file
        return langConfig.contains("prefix") && 
            langConfig.contains("enabling-message") && 
            langConfig.contains("finished-enabling");
    }
}