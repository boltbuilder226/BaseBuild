package com.utils;

import java.util.HashMap;
import java.util.UUID;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class GUIManager implements Listener {

	private HashMap<UUID, Boolean> hasGUIopen = new HashMap<UUID, Boolean>();
	private Inventory maininv;
	private Inventory bt1;
	private Inventory bt2;
	private Inventory bt3;
	private ItemUtils iu = new ItemUtils();
	private ItemStack BaseT1 = new ItemStack(Material.CHEST);
	private ItemStack BaseT2 = new ItemStack(Material.CHEST);
	private ItemStack BaseT3 = new ItemStack(Material.CHEST);

	private ItemStack BaseT1_1 = new ItemStack(Material.REDSTONE);
	private ItemStack BaseT1_2 = new ItemStack(Material.REDSTONE);
	private ItemStack BaseT1_3 = new ItemStack(Material.REDSTONE);
	private ItemStack BaseT1_4 = new ItemStack(Material.REDSTONE);

	private ItemStack BaseT2_1 = new ItemStack(Material.REDSTONE);
	private ItemStack BaseT2_2 = new ItemStack(Material.REDSTONE);
	private ItemStack BaseT2_3 = new ItemStack(Material.REDSTONE);
	private ItemStack BaseT2_4 = new ItemStack(Material.REDSTONE);

	private ItemStack BaseT3_1 = new ItemStack(Material.REDSTONE);
	private ItemStack BaseT3_2 = new ItemStack(Material.REDSTONE);
	private ItemStack BaseT3_3 = new ItemStack(Material.REDSTONE);
	private ItemStack BaseT3_4 = new ItemStack(Material.REDSTONE);

	private ItemStack back = new ItemStack(Material.ARROW);

	private JavaPlugin core;

	public GUIManager(JavaPlugin core) {
		this.core = core;
		core.getServer().getPluginManager().registerEvents(this, core);
		maininv = core.getServer().createInventory(null, InventoryType.CHEST);
		bt1 = core.getServer().createInventory(null, InventoryType.CHEST);
		bt2 = core.getServer().createInventory(null, InventoryType.CHEST);
		bt3 = core.getServer().createInventory(null, InventoryType.CHEST);

		ItemUtils.setName(BaseT1, ChatColor.BLUE + "Bases Level 1");
		ItemUtils.setName(BaseT2, ChatColor.YELLOW + "Bases Level 2");
		ItemUtils.setName(BaseT3, ChatColor.RED + "Bases Level 3");
		ItemUtils.setLore(
				BaseT1,
				iu.newLoreGenerator()
						.addLine(
								ChatColor.GREEN
										+ "Choose Bases which are Level 1.")
						.getLore());
		ItemUtils.setLore(
				BaseT2,
				iu.newLoreGenerator()
						.addLine(
								ChatColor.GREEN
										+ "Choose Bases which are Level 2.")
						.getLore());
		ItemUtils.setLore(
				BaseT3,
				iu.newLoreGenerator()
						.addLine(
								ChatColor.GREEN
										+ "Choose Bases which are Level 3.")
						.getLore());

		ItemUtils.setName(BaseT1_1, ChatColor.GREEN + "[1] Base 1");
		ItemUtils.setName(BaseT1_2, ChatColor.GREEN + "[1] Base 2");
		ItemUtils.setName(BaseT1_3, ChatColor.GREEN + "[1] Base 3");
		ItemUtils.setName(BaseT1_4, ChatColor.GREEN + "[1] Base 4");

		ItemUtils.setName(BaseT2_1, ChatColor.GREEN + "[2] Base 1");
		ItemUtils.setName(BaseT2_2, ChatColor.GREEN + "[2] Base 2");
		ItemUtils.setName(BaseT2_3, ChatColor.GREEN + "[2] Base 3");
		ItemUtils.setName(BaseT2_4, ChatColor.GREEN + "[2] Base 4");

		ItemUtils.setName(BaseT3_1, ChatColor.GREEN + "[3] Base 1");
		ItemUtils.setName(BaseT3_2, ChatColor.GREEN + "[3] Base 2");
		ItemUtils.setName(BaseT3_3, ChatColor.GREEN + "[3] Base 3");
		ItemUtils.setName(BaseT3_4, ChatColor.GREEN + "[3] Base 4");

		ItemUtils.setName(back, ChatColor.GOLD + "Back");

		maininv.setItem(11, BaseT1);
		maininv.setItem(13, BaseT2);
		maininv.setItem(15, BaseT3);
		maininv.setItem(26, back);

		bt1.setItem(11, BaseT1_1);
		bt1.setItem(12, BaseT1_2);
		bt1.setItem(14, BaseT1_3);
		bt1.setItem(15, BaseT1_4);
		bt1.setItem(26, back);

		bt2.setItem(11, BaseT2_1);
		bt2.setItem(12, BaseT2_2);
		bt2.setItem(14, BaseT2_3);
		bt2.setItem(15, BaseT2_4);
		bt2.setItem(26, back);

		bt3.setItem(11, BaseT3_1);
		bt3.setItem(12, BaseT3_2);
		bt3.setItem(14, BaseT3_3);
		bt3.setItem(15, BaseT3_4);
		bt3.setItem(26, back);
	}

	public void openGUI(Player p) {
		hasGUIopen.put(p.getUniqueId(), true);

		p.openInventory(maininv);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		UUID up = p.getUniqueId();
		if (hasGUIopen.containsKey(up))
			if (hasGUIopen.get(up)) {
				Inventory i = e.getInventory();
				ItemStack clicked = e.getCurrentItem();
				if (i.equals(maininv)) {
					if (clicked.equals(BaseT1)) {
						p.openInventory(bt1);
					} else if (clicked.equals(BaseT2)) {
						p.openInventory(bt2);
					} else if (clicked.equals(BaseT3)) {
						p.openInventory(bt3);
					} else if (clicked.equals(back)) {
						p.closeInventory();
					}
				} else if (i.equals(bt1)) {
					if (clicked.equals(BaseT1_1)) {
						// Base Tier 1 - 1 Base
						p.sendMessage("You selected [1] Base 1");
					}
					if (clicked.equals(BaseT1_2)) {
						// Base Tier 1 - 2 Base
						p.sendMessage("You selected [1] Base 2");
					}
					if (clicked.equals(BaseT1_3)) {
						// Base Tier 1 - 3 Base
						p.sendMessage("You selected [1] Base 3");
					}
					if (clicked.equals(BaseT1_4)) {
						// Base Tier 1 - 4 Base
						p.sendMessage("You selected [1] Base 4");
					} else if (clicked.equals(back)) {
						p.openInventory(maininv);
					}
				} else if (i.equals(bt2)) {
					if (clicked.equals(BaseT2_1)) {
						// Base Tier 2 - 1 Base
						p.sendMessage("You selected [2] Base 1");
					}
					if (clicked.equals(BaseT2_2)) {
						// Base Tier 2 - 2 Base
						p.sendMessage("You selected [2] Base 2");
					}
					if (clicked.equals(BaseT2_3)) {
						// Base Tier 2 - 3 Base
						p.sendMessage("You selected [2] Base 3");
					}
					if (clicked.equals(BaseT2_4)) {
						// Base Tier 2 - 4 Base
						p.sendMessage("You selected [2] Base 4");
					} else if (clicked.equals(back)) {
						p.openInventory(maininv);
					}
				} else if (i.equals(bt3)) {
					if (clicked.equals(BaseT3_1)) {
						// Base Tier 3 - 1 Base
						p.sendMessage("You selected [3] Base 1");
					}
					if (clicked.equals(BaseT3_2)) {
						// Base Tier 3 - 2 Base
						p.sendMessage("You selected [3] Base 2");
					}
					if (clicked.equals(BaseT3_3)) {
						// Base Tier 3 - 3 Base
						p.sendMessage("You selected [3] Base 3");
					}
					if (clicked.equals(BaseT3_4)) {
						// Base Tier 3 - 4 Base
						p.sendMessage("You selected [3] Base 4");
					} else if (clicked.equals(back)) {
						p.openInventory(maininv);
					}
				}
				e.setCancelled(true);
			}
	}

	private void closeGUI(Player p) {
		hasGUIopen.remove(p.getUniqueId());
		p.closeInventory();
	}

}
