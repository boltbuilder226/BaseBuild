package com.listeners;

import java.math.BigDecimal;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.UserDoesNotExistException;
import com.utils.BaseBuildEvent;

public class PlayerListener implements Listener {

	@EventHandler
	public void onBaseBuild(BaseBuildEvent e) {

		Player player = e.getPlayer();
		int cost = e.getPrice();
		if (cost <= 0) {
			return;
		}
		try {
			BigDecimal bal = Economy.getMoneyExact(player.getName());
			BigDecimal price = BigDecimal.valueOf((long) cost);
			int cmp = bal.compareTo(price);
			
			if (cmp == 0 || cmp == 1) {
				
				try {
					Economy.substract(player.getName(), price);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			} else {
				player.sendMessage(ChatColor.RED + "You do not have enough money to do this!"); 
			}

		} catch (UserDoesNotExistException e1) {
			e1.printStackTrace();
		}

	}

}
