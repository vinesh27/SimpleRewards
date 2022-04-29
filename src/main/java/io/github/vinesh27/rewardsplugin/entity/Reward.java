package io.github.vinesh27.rewardsplugin.entity;

import org.bukkit.inventory.ItemStack;

public record Reward(ItemStack[] items, String[] commands) {
    public ItemStack[] getItems() { return items; }
    public String[] getCommands() { return commands; }
}
