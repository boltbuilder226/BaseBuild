package com.utils;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.schematic.SchematicFormat;

@SuppressWarnings({ "deprecation" })
public class SchematicParser {

	public static final BlockFace[] axis = { BlockFace.NORTH, BlockFace.EAST,
			BlockFace.SOUTH, BlockFace.WEST };
	public static final BlockFace[] radial = { BlockFace.NORTH,
			BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST,
			BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST,
			BlockFace.NORTH_WEST };

	public static CuboidClipboard load(File file) {

		try {
			SchematicFormat format = SchematicFormat.getFormat(file);
			CuboidClipboard c = SchematicFormat.MCEDIT.load(file);
			return c;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public static void paste(CuboidClipboard c, Location loc) {

		try {
			org.bukkit.util.Vector vec = loc.toVector();

			BlockFace direction = yawToFace(loc.getYaw(), false);
			
			if (direction == BlockFace.NORTH) {
				c.rotate2D(270);
				vec.setZ(vec.getZ() - 1);
				vec.setX(vec.getX() - (c.getWidth() / 2));
			} else if (direction == BlockFace.SOUTH) {
				c.rotate2D(90);
				vec.setZ(vec.getZ() + 1);
				vec.setX(vec.getX() + (c.getWidth() / 2));
			} else if (direction == BlockFace.EAST) {
				vec.setX(vec.getX() + 1);
				vec.setZ(vec.getZ() - (c.getLength() / 2));
			} else if (direction == BlockFace.WEST) {
				c.rotate2D(180);
				vec.setX(vec.getX() - 1);
				vec.setZ(vec.getZ() + (c.getLength() / 2));
			}
			
			Vector vecFin = BukkitUtil.toVector(vec);
			
			EditSession session = new EditSession(new BukkitWorld(
					loc.getWorld()), 999999999);
			c.paste(session, vecFin, false);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static BlockFace yawToFace(float yaw,
			boolean useSubCardinalDirections) {
		if (useSubCardinalDirections) {
			return radial[Math.round(yaw / 45f) & 0x7];
		} else {
			return axis[Math.round(yaw / 90f) & 0x3];
		}
	}

}
