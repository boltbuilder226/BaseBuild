package com.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Main;
import com.Perms;

public class Base implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("base")) {
			
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You can only use this command in-game!");
				return false;
			}
			Player player = (Player) sender;
			if (!player.hasPermission(Perms.Build.getPerm())) {
				player.sendMessage(ChatColor.RED + "You can only use this command in-game!");
				return false;
			}
			Main.gui.openGUI(player);
			
		}
		
		return false;
	}

	
}
