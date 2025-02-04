package com.joeltrauger.leatherworkshop.events;

import com.joeltrauger.leatherworkshop.LeatherWorkshop;

import java.util.Random;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Event_ZombieSmelt implements Listener {
    private final LeatherWorkshop plugin;

    public Event_ZombieSmelt(LeatherWorkshop plugin) {
        this.plugin = plugin;
    }

    // Prevent inserting rotten flesh into the furnace input slot if the player lacks permission
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getType() == InventoryType.FURNACE) {
            Player player = (Player) event.getWhoClicked();
            Inventory furnaceInventory = event.getInventory();
            int inputSlot = 0;  // Furnace input slot is always slot 0

            // Handle shift-click (moving items automatically)
            if (event.isShiftClick()) {
                ItemStack clickedItem = event.getCurrentItem();
                if (clickedItem != null && clickedItem.getType() == Material.ROTTEN_FLESH) {
                    // Check where the item would go when shift-clicked
                    int targetSlot = furnaceInventory.firstEmpty();
                    if (targetSlot == inputSlot && !player.hasPermission("leatherworkshop.recipe")) {
                        event.setCancelled(true);
                        player.sendMessage(plugin.getLangFile().getMessage("no-recipe-permission", "You do not have permission to smelt rotten flesh into leather.", true));
                    }
                }
            }

            // Handle normal item placement (drag and drop)
            ItemStack cursorItem = event.getCursor();
            if (event.getSlot() == inputSlot && cursorItem != null && cursorItem.getType() == Material.ROTTEN_FLESH) {
                if (!player.hasPermission("leatherworkshop.recipe")) {
                    event.setCancelled(true);
                    player.sendMessage(plugin.getLangFile().getMessage("no-recipe-permission", "You do not have permission to smelt rotten flesh into leather.", true));
                }
            }
        }
    }

    // Prevent dragging rotten flesh into the furnace input slot if the player lacks permission
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getInventory().getType() == InventoryType.FURNACE) {
            Player player = (Player) event.getWhoClicked();
            ItemStack draggedItem = event.getOldCursor();

            int inputSlot = 0; // Furnace input slot

            // Only block dragging if the drag affects the furnace input slot
            if (event.getInventorySlots().contains(inputSlot) && draggedItem != null && draggedItem.getType() == Material.ROTTEN_FLESH) {
                if (!player.hasPermission("leatherworkshop.recipe")) {
                    event.setCancelled(true);
                    player.sendMessage(plugin.getLangFile().getMessage("no-recipe-permission", "You do not have permission to smelt rotten flesh into leather.", true));
                }
            }
        }
    }

    // Handle smelting when permission is granted
    @EventHandler
    public void onBurn(FurnaceSmeltEvent e) {
        if (e.getSource().getType() == Material.ROTTEN_FLESH) {
            int chance = plugin.getConfig().getInt("leather-chance");

            int rand = (new Random()).nextInt(100);
            if (rand < chance) {
                ItemStack result = new ItemStack(Material.LEATHER, 1);
                e.setResult(result);
            } else {
                e.setResult(new ItemStack(Material.AIR));
            }
        }
    }
}
