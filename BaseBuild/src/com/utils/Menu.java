package com.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.Main;
import com.utils.IconMenu.OptionClickEvent;

public class Menu {

	private static IconMenu menu = new IconMenu("Choose a base", 27,
			new IconMenu.OptionClickEventHandler() {

				@Override
				public void onOptionClick(OptionClickEvent event) {
					Player player = event.getPlayer();
					String name = event.getName().toLowerCase();
					if (name.equalsIgnoreCase("Small")) {
						small(player);
					} else if (name.equalsIgnoreCase("Medium")) {
						medium(player);
					} else if (name.equalsIgnoreCase("Large")) {
						large(player);
					}
				}
			}, Main.instance);

	public static void open(Player player) {
		fillMenu();
		menu.open(player);

	}

	private static void fillMenu() {
		menu.setOption(0, new ItemStack(Material.IRON_INGOT, 1), "Small",
				"Small bases");
		menu.setOption(1, new ItemStack(Material.GOLD_INGOT, 1), "Medium",
				"Medium bases");
		menu.setOption(2, new ItemStack(Material.DIAMOND, 1), "Large",
				"Large bases");

	}

	private static void small(Player player) {
		
	}

	private static void medium(Player player) {

	}

	private static void large(Player player) {

	}

}
