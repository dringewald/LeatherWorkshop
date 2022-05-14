package com.joeltrauger.leatherworkshop;

import java.io.File;
import java.io.IOException;
import com.joeltrauger.leatherworkshop.events.Event_ZombieSmelt;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public final class LeatherWorkshop extends JavaPlugin {
	
	public static LeatherWorkshop main;
	File file = new File(getDataFolder() + "/LeatherWorkshop.yml");
	public FileConfiguration config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);
	
	@Override
	public void onEnable() {
		//add config
		getServer().getConsoleSender().sendMessage("Loading LeatherWorker...");
		main = this;
		this.config.addDefault("Chance of Leather[1-100]", 20);
		this.config.addDefault("Cook speed (default 200)", 200);
		this.config.options().copyDefaults(true);
		try {
	      this.config.save(this.file);
	    } catch (IOException e) {
	      e.printStackTrace();
	    } 
		onEvents();
		onRecipe();
		getServer().getConsoleSender().sendMessage("Loading LeatherWorker...DONE");
	}
	
	@Override
	public void onDisable() {
		//insert logic to remove the crafting recipes
	}

	public void onEvents() {
		PluginManager pm = Bukkit.getPluginManager();
	    pm.registerEvents((Listener)new Event_ZombieSmelt(), (Plugin)this);
	    getServer().getConsoleSender().sendMessage("Registered new furnace event listener.");
	}
	
	public void onRecipe() {
		getServer().getConsoleSender().sendMessage("Loading LeatherWorker Zombie Flesh Recipe...");
		ItemStack is = new ItemStack(Material.LEATHER, 1);
		NamespacedKey nkey = new NamespacedKey(main, "ZombieLeather");
		int cookSpeed = LeatherWorkshop.main.config.getInt("Cook speed (default 200)");
		FurnaceRecipe fr = new FurnaceRecipe(nkey, is, Material.ROTTEN_FLESH, 0, cookSpeed);
		Bukkit.getServer().addRecipe(fr);
		getServer().getConsoleSender().sendMessage("LeatherWorker is done loading recipes.");
	}
	
}
