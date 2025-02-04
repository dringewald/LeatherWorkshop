package com.joeltrauger.leatherworkshop.events;

import com.joeltrauger.leatherworkshop.LeatherWorkshop;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.ItemStack;

public class Event_ZombieSmelt implements Listener {
    private LeatherWorkshop plugin;

    public Event_ZombieSmelt(LeatherWorkshop plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onBurn(FurnaceSmeltEvent e) {
        if (e.getSource().getType() == Material.ROTTEN_FLESH) {
            int chance = plugin.getConfig().getInt("leather-chance");

            int rand = (new Random()).nextInt(100);
            if (rand < chance) {
                ItemStack result = e.getResult();
                result.setAmount(1);
                e.setResult(result);
            } else {
                ItemStack result = e.getResult();
                result.setAmount(0);
                e.setResult(result);
            }
        }
    }
}