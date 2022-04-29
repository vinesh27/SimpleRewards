package io.github.vinesh27.rewardsplugin.util;

import io.github.vinesh27.rewardsplugin.RewardsPlugin;
import io.github.vinesh27.rewardsplugin.entity.Reward;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Util {
    static RewardsPlugin plugin = RewardsPlugin.getInstance();
    
    /**
     * To get a Player's rank by checking what permission they have
     * @param player The player to get the rank of
     * @return String The rank of the player
     */
    public static String getPlayerRank(Player player) {
        if(player.hasPermission("rewards.rank.five"))
            return "five";
        if(player.hasPermission("rewards.rank.four"))
            return "four";
        if(player.hasPermission("rewards.rank.three"))
            return "three";
        if(player.hasPermission("rewards.rank.two"))
            return "two";
        if(player.hasPermission("rewards.rank.one"))
            return "one";
        else return null;
    }
    
    /**
     * Gives you a Reward, with the commands to execute & the ItemStack[] to give to the player
     * @param rank The rank of the player in string
     * @return <link Reward>
     */
    public static Reward getRewardsByRank(String rank) {
        ConfigurationSection rankRewards = (ConfigurationSection) plugin.getConfig().get("rewards." + rank);
        List<String> items = rankRewards.getStringList("items");
        ItemStack[] itemStacks = new ItemStack[items.size()];
        
        for (String item : items) {
            String material = item.split(":")[0];
            int amount = Integer.parseInt(item.split(":")[1]);
            ItemStack itemStack = new ItemStack(Material.getMaterial(material), amount);
            itemStacks[items.indexOf(item)] = itemStack;
        }
        
        String[] commands = rankRewards.getStringList("commands").toArray(new String[0]);
        
        return new Reward(itemStacks, commands);
    }
    
    /**
     * Gives array of ItemStacks to display in the GUI from the config
     * @return ItemStack[] Array of size 9 which contains all the items to display in the GUI
     */
    public static ItemStack[] getRewardGUI() {
        ConfigurationSection gui = (ConfigurationSection) plugin.getConfig().get("gui");
        ConfigurationSection rankRewards = (ConfigurationSection) gui.get("rankRewards");
        ConfigurationSection regular = (ConfigurationSection) rankRewards.get("regular");
        ConfigurationSection vip = (ConfigurationSection) rankRewards.get("vip");
        ConfigurationSection vipPlus = (ConfigurationSection) rankRewards.get("vipPlus");
        ConfigurationSection mvp = (ConfigurationSection) rankRewards.get("mvp");
        ConfigurationSection mvpPlus = (ConfigurationSection) rankRewards.get("mvpPlus");

        ConfigurationSection voteReward = (ConfigurationSection) gui.get("voteReward");
        

        ConfigurationSection[] sections = {regular, vip, vipPlus, mvp, mvpPlus, voteReward};
        
        ItemStack[] items = new ItemStack[9];
        {
            int i = 0;
            for(ConfigurationSection section : sections) {
                ItemStack itemStack = new ItemStack(Material.getMaterial(section.getString("material")));
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(section.getString("name"));
                itemMeta.setLore(section.getStringList("description"));
                itemStack.setItemMeta(itemMeta);
                
                items[i] = itemStack;
                i++;
            }
        }

        ItemStack bg = new ItemStack(Material.getMaterial(gui.getString("bgMaterial")));
        int[] glassSlots = {0, 6, 8};
        for (int glassSlot : glassSlots) items[glassSlot] = bg;
        
        return items;
    }
    
    /**
     * To get the message which will be displayed when a player clicks on a reward
     * @param reward The rank of the player in string
     * @return String The message to be displayed
     */
    public static String getMessage(String reward) {
        if(reward.equals("vote"))
            return plugin.getConfig().getString("gui.voteReward.message");
        return plugin.getConfig().getString("gui.rankRewards" + reward + ".message");
    }
    
    public static boolean hasBeenRewarded(Player player, String rank) {
        return false;
    }
    
    public static void reward(Player player, String rank) {
        // Check if it has been 24 hours since the player was rewarded
        if(!hasBeenRewarded(player, rank)) {
            Reward reward = getRewardsByRank(rank);
            for (ItemStack itemStack : reward.getItems())
                player.getWorld()
                    .dropItemNaturally(player.getLocation(), itemStack)
                    .setOwner(player.getUniqueId());
            for (String command : reward.getCommands())
                Bukkit.dispatchCommand(
                    Bukkit.getConsoleSender(),
                    command.replace("{player}", player.getName())
                );
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', getMessage(rank)));
        } else {
            player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1, 1);
            player.sendMessage(ChatColor.RED + "You have already been rewarded today!");
        }
    }
}
