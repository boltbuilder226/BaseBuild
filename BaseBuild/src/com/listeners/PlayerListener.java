package com.listeners;

import java.math.BigDecimal;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.Main;
import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.UserDoesNotExistException;
import com.utils.BaseBuildEvent;

public class PlayerListener implements Listener {

	@EventHandler
	public void onBaseBuild(BaseBuildEvent e) {
		Player player = e.getPlayer();
		double cost = e.getPrice();
		if (cost <= 0) {
			return;
		}
		if (Main.hasEss) {
			try {
				BigDecimal bal = Economy.getMoneyExact(player.getName());
				BigDecimal price = BigDecimal.valueOf(cost);
				int cmp = bal.compareTo(price);

				if (cmp == 0 || cmp == 1) {

					try {
						Economy.substract(player.getName(), price);
						player.sendMessage(ChatColor.GREEN + "$" + cost
								+ " has been taken from your balance");
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				} else {
					player.sendMessage(ChatColor.RED
							+ "You do not have enough money to do this!");
				}

			} catch (UserDoesNotExistException e1) {
				e1.printStackTrace();
			}
		}
		if (Main.hasVault) {
			EconomyResponse r = Main.econ.withdrawPlayer(player, cost);
			if (r.transactionSuccess()) {
				player.sendMessage(ChatColor.GREEN + "$" + cost + " has been taken from your balance");
			} else {
				player.sendMessage(ChatColor.RED + String.format("An error occured: %s",
						r.errorMessage));
			}
		}

	}

}
