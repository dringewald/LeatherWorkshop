package com.joeltrauger.leatherworkshop.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;

import com.joeltrauger.leatherworkshop.LeatherWorkshop;

public class LangFile extends PluginFile {
    
    public LangFile(LeatherWorkshop plugin, String language) {
        super(plugin, "lang/" + language + ".yml");
    }

    public String getMessage(String configPath, String defaultValue, boolean withPrefix) {
        String message = getFileConfig().getString(configPath);
        if (message == null || message.isEmpty()) {
            message = defaultValue;
        }
        
        if (withPrefix) {
            String prefix = getFileConfig().isString("prefix") ? getFileConfig().getString("prefix") + " " : "";
            message = prefix + message;
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public List<String> getMessageList(String configPath) {
        List<String> rawMessages = getFileConfig().getStringList(configPath);
        if (rawMessages == null) {
            return new ArrayList<>();
        }

        List<String> messages = new ArrayList<>();
        for (String message : rawMessages) {
            messages.add(ChatColor.translateAlternateColorCodes('&', message));
        }

        return messages;
    }

    public List<String> getMessageList(String configPath, String... defaultList) {
        List<String> messages = getMessageList(configPath);
        if (messages.isEmpty()) {
            return Arrays.asList(defaultList);
        }

        return messages;
    }
}
