package io.github.vinesh27.rewardsplugin;

import io.github.vinesh27.rewardsplugin.commands.RewardCommand;
import io.github.vinesh27.rewardsplugin.gui.RewardGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class RewardsPlugin extends JavaPlugin {
    private static RewardsPlugin instance;
    public static RewardsPlugin getInstance() { return instance; }
    
    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Loading " + ChatColor.GOLD + "RewardsPlugin...");
        instance = this;
        this.getCommand("rewards").setExecutor(new RewardCommand());
        this.getServer().getPluginManager().registerEvents(new RewardGUI(), this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Loaded " + ChatColor.GOLD + "RewardsPlugin");
    }
    
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
