package io.github.vinesh27.rewardsplugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class RewardsPlugin extends JavaPlugin {
    private static RewardsPlugin instance;
    public static RewardsPlugin getInstance() { return instance; }
    
    @Override
    public void onEnable() {
        // Plugin startup logic
        
    }
    
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
