package com;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.commands.Base;
import com.listeners.PlayerListener;

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
    
    public static boolean hasEss, hasVault;
	
	public void onEnable(){
		
		instance = this;
		createFiles();
		
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		getCommand("base").setExecutor(new Base());
		hasEss = hasEss();
		hasVault = hasVault();
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + m1);
		
	}
	
	public void onDisable(){
		
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
		
//		Loading
		try {
			config.load(configf);
			prices.load(pricesf);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

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
		if (setupEconomy()) {
			setupChat();
			setupPermissions();
			return true;
		}
		return false;
	}
	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
	
}
