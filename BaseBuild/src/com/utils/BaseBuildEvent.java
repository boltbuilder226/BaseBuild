package com.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BaseBuildEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	private Player player;
	private Location loc;
	private double price;
	
	public BaseBuildEvent(Player player, Location loc, double price) {
		this.player = player;
		this.loc = loc;
		this.price = price;
	}
	
	@Override
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public double getPrice() {
		return price;
	}
	
	

}
