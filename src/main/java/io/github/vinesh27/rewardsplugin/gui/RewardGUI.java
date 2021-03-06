package io.github.vinesh27.rewardsplugin.gui;

import io.github.vinesh27.rewardsplugin.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class RewardGUI implements Listener {
    private final Inventory inv;
    
    public RewardGUI() {
        inv = Bukkit.createInventory(null, 9, "Daily Rewards");
        initializeItems();
    }
    
    public void initializeItems() {
        ItemStack[] items = Util.getRewardGUI();
        for (int i = 0; i < items.length; i++) inv.setItem(i, items[i]);
    }
    
    // To open the inventory
    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }
    
    // To check for clicks in our inventory
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!e.getInventory().equals(inv)) return;
        e.setCancelled(true);
        
        int[] bgSlots = {0, 6, 8};
        for (int slot : bgSlots)
            if (e.getSlot() == slot) return;
        
        Util.reward(
            (Player) e.getWhoClicked(),
            Util.getPlayerRank(
                (Player) e.getWhoClicked()
            )
        );
    }
    
    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryDrag(final InventoryDragEvent e) {
        if (e.getInventory().equals(inv)) e.setCancelled(true);
    }
}