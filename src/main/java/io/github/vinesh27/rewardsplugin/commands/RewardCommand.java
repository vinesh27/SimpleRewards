package io.github.vinesh27.rewardsplugin.commands;

import io.github.vinesh27.rewardsplugin.gui.RewardGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RewardCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        new RewardGUI().openInventory((Player) sender);
        return true;
    }
}
