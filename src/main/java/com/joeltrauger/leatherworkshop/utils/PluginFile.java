package com.joeltrauger.leatherworkshop.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.joeltrauger.leatherworkshop.LeatherWorkshop;

import java.io.File;
import java.io.IOException;


public abstract class PluginFile {

    private File file;
    private FileConfiguration fileConfig;

    public PluginFile(LeatherWorkshop plugin, String name) {
        file = new File(plugin.getDataFolder(), name);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource(name, false);
        }

        reload();
    }

    public void save() {
        try {
            fileConfig.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void reload() {
        fileConfig = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getFileConfig() {
        return fileConfig;
    }

    public File getFile() {
        return file;
    }
}
