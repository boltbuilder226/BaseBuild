package com;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.commands.Base;
import com.listeners.PlayerListener;
import com.utils.GUIManager;

public class Main extends JavaPlugin {

	public static Main instance;
	public static String m1 = "Enabled BaseBuild!";
	public static String m2 = "Disabled BaseBuild!";

	public static File configf;
	public static FileConfiguration config;

	public static File pricesf;
	public static FileConfiguration prices;

	public static Economy econ = null;
	public static Permission perms = null;
	public static Chat chat = null;

	public static GUIManager gui;

	public static boolean hasEss, hasVault;

	public static ArrayList<File> schematics;
	
	public void onEnable() {

		instance = this;
		createFiles();

		getServer().getPluginManager().registerEvents(new PlayerListener(),
				this);
		getCommand("base").setExecutor(new Base());
		hasEss = hasEss();
		hasVault = hasVault();
		gui = new GUIManager(this);

		Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + m1);

	}

	public void onDisable() {

		instance = null;
		Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + m2);

	}

	public void createFiles() {

		// Initialization
		configf = new File(getDataFolder(), "config.yml");
		pricesf = new File(getDataFolder(), "prices.yml");

		if (!configf.exists()) {
			configf.getParentFile().mkdirs();
			copy(getResource("config.yml"), configf);
		}
		if (!pricesf.exists()) {
			pricesf.getParentFile().mkdirs();
			copy(getResource("prices.yml"), pricesf);
		}

		config = new YamlConfiguration();
		prices = new YamlConfiguration();

		// Loading
		try {
			config.load(configf);
			prices.load(pricesf);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Schematic copy
		File dir = new File(getDataFolder(), "\\schematics");
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File[] tiers = new File[3];
		
		for (int i = 0; i < 3; i++) {
			tiers[i] = new File(dir, "\\"
					+ config.getString("bases." + (i + 1) + ".title"));
			if (!tiers[i].exists()) {
				tiers[i].mkdirs();
			}
		}

		File[] files = new File[12];
		for (int i = 0; i < 12; i++) {

			String name = config.getString("bases." + ((i / 4) + 1) + "."
					+ ((i % 4) + 1));
			String filename = name + ".schematic";

			files[i] = new File(tiers[i / 4], filename);
			try {
				if (!files[i].exists()) {

					files[i].createNewFile();
					InputStream is = getResource("schematics/Tier" + ((i / 4) + 1) + "/Base " + ((i % 4) + 1) + ".schematic");
					OutputStream os = new FileOutputStream(files[i]);
					if (is == null) {
						continue;
					}
					IOUtils.copy(is, os);
					
				}
			} catch (IOException e) {
				System.out.println(i);
			}

		}

		schematics = new ArrayList<File>(Arrays.asList(files));
		
	}

	public void copy(InputStream in, File file) {

		try {

			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {

				out.write(buf, 0, len);

			}
			out.close();
			in.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public boolean hasEss() {

		return getServer().getPluginManager().getPlugin("Essentials") != null;

	}

	public boolean hasVault() {
		if (!setupEconomy()) {
			return false;
		}
		if (!setupChat()) {
			return false;
		}
		if (!setupPermissions()) {
			setupPermissions();
		}
		return true;
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer()
				.getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager()
				.getRegistration(Chat.class);
		if (rsp == null) {
			return false;
		}
		chat = rsp.getProvider();
		return chat != null;
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer()
				.getServicesManager().getRegistration(Permission.class);
		if (rsp == null) {
			return false;
		}
		perms = rsp.getProvider();
		return perms != null;
	}

}
