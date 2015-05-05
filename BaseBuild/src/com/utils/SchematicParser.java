package com.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.CommandBlock;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import com.sk89q.jnbt.CompoundTag;
import com.sk89q.jnbt.Tag;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.blocks.BaseItemStack;
import com.sk89q.worldedit.blocks.ChestBlock;
import com.sk89q.worldedit.blocks.SignBlock;
import com.sk89q.worldedit.schematic.SchematicFormat;

@SuppressWarnings({"deprecation"})
public class SchematicParser {
	
	public static CuboidClipboard load(File file) {
		
		try {
			CuboidClipboard c = SchematicFormat.MCEDIT.load(file);
			return c;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public static void paste(CuboidClipboard c, Location loc) {
		
		if (c == null) {
			System.out.println("Error in reading schematic. Are you sure the file is a schematic and not corrupted?");
		}
		
		int max_x = c.getWidth();
		int max_y = c.getHeight();
		int max_z = c.getLength();
		
		HashMap<Location, BaseBlock> blockmap = new HashMap<Location, BaseBlock>();
		
		for (int y = loc.getBlockY(); y < max_y; y++) {
			
			for (int x = loc.getBlockX() - (max_x / 2); x < loc.getBlockX() + (max_x / 2); x++) {
				
				for (int z = loc.getBlockZ() - (max_z / 2); z < loc.getBlockZ() + (max_z / 2); z++) {
					
					Vector v = new Vector(x, y, z);
					Location l = new Location(loc.getWorld(), x, y, z);
					BaseBlock block = c.getBlock(v);
					blockmap.put(l, block);
					
				}
				
			}
			
		}
		
		for (Location l : blockmap.keySet()) {
			
			BaseBlock b = blockmap.get(l);
			Block block = l.getBlock();
			BlockState bs = block.getState();
			
			Material m = Material.getMaterial(b.getId());
			int data = b.getData();
			CompoundTag nbt = null;
			bs.setType(m);
			bs.setRawData((byte) data);
			bs.update(true, false);
			
			if (b.hasNbtData()) {
				nbt = b.getNbtData();
			}
			
			try {
				
				if (b.hasNbtData()) {
					
					if (m == Material.SIGN_POST || m == Material.WALL_SIGN) {
						
						SignBlock sb = new SignBlock(m.getId(), data);
						sb.setNbtData(nbt);
						org.bukkit.block.Sign sign = (org.bukkit.block.Sign) bs;
						String[] text = sb.getText();
						for (int i = 0; i < text.length; i++) {
							
							sign.setLine(i, text[i]);
							
						}
						sign.update(true, false);
						
					} else if (m == Material.COMMAND) {
						
						Map<String, Tag> map = nbt.getValue();
						CommandBlock cb = (CommandBlock) bs;
						if (map.containsKey("Command")) {
							Object cmdval = map.get("Command").getValue();
							cb.setCommand(cmdval.toString());
						}
						if (map.containsKey("Custome Name")) {
							Object nameval = map.get("Custom Name").getValue();
							cb.setName(nameval.toString());
						}
						
						
						
						
					} else if (m == Material.CHEST) {
						
						ChestBlock cb = new ChestBlock(data);
						cb.setNbtData(nbt);
						Chest chest = (Chest) bs;
						BaseItemStack[] bis_array = cb.getItems();
						ArrayList<ItemStack> inv = new ArrayList<ItemStack>();
						for (BaseItemStack bis : bis_array) {
							
							ItemStack is = new ItemStack(Material.getMaterial(bis.getType()), bis.getAmount());
							is.setDurability(bis.getDamage());
							LinkedHashMap<Enchantment, Integer> map = new LinkedHashMap<Enchantment, Integer>();
							for (Integer i : bis.getEnchantments().keySet()) {
								
								map.put(Enchantment.getById(i), bis.getEnchantments().get(i));
								
							}
							is.addEnchantments(map);
							inv.add(is);
							
						}
						chest.getBlockInventory().setContents((ItemStack[]) inv.toArray());
						chest.update(true, false);
						
					}
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
	}

}
