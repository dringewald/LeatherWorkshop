package com.joeltrauger.leatherworkshop.events;

import java.util.Random;
import com.joeltrauger.leatherworkshop.LeatherWorkshop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.ItemStack;

public class Event_ZombieSmelt implements Listener{
	@EventHandler
	public void onBurn(FurnaceSmeltEvent e) {
		if(e.getSource().getType() == Material.ROTTEN_FLESH) { //if material is rotten flesh
			int chance = LeatherWorkshop.main.config.getInt("Chance of Leather[1-100]"); //get chance from config file
			//Bukkit.getServer().getConsoleSender().sendMessage("chance = " + chance);
			int rand = new Random().nextInt(100); //get new random number from 1-100
			if(rand < chance) { //if random is less than chance, then
				ItemStack result = e.getResult();
				result.setAmount(1); // get one item
				e.setResult(result);
			}else {
				ItemStack result = e.getResult();
				result.setAmount(0); // get nothing
				e.setResult(result);
			}
		}
	}
}
