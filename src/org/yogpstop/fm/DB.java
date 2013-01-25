package org.yogpstop.fm;

import java.util.HashMap;

import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class DB {
	private static HashMap<String, Integer> items = new HashMap<String, Integer>();
	private static HashMap<String, Integer> blocks = new HashMap<String, Integer>();
	protected static HashMap<Integer, ItemMeta> ims = new HashMap<Integer, ItemMeta>();
	protected static HashMap<Integer, BlockMeta> bms = new HashMap<Integer, BlockMeta>();

	private static int currentItemMetadata = 1;
	private static int currentBlockMetadata = 1;

	public static void addItem(ItemMeta im) {
		items.put(im.getItemName(), currentItemMetadata);
		ims.put(currentItemMetadata, im);
		currentItemMetadata++;
	}

	public static void addBlock(BlockMeta bm) {
		blocks.put(bm.getBlockName(), currentBlockMetadata);
		bms.put(currentBlockMetadata, bm);
		currentBlockMetadata++;
	}

	public static Integer getItem(String name) {
		return items.get(name);
	}

	public static Integer getBlock(String name) {
		return blocks.get(name);
	}

	@SideOnly(Side.CLIENT)
	public static String getTextureFileFromDamage(int par1) {
		return ims.get(par1).getTextureFile();
	}

	@SideOnly(Side.CLIENT)
	public static int[] getTextureCoordFromDamage(int par1) {
		return ims.get(par1).getIcon();
	}

	public static boolean isNeedFuel(int meta) {
		return ims.get(meta).isNeedFuel();
	}

	public static boolean isCookware(int meta) {
		return ims.get(meta).isCookware();
	}

	public static boolean isPlantable(int meta) {
		return ims.get(meta).isPlantable();
	}

	public static boolean isEatable(int meta) {
		return ims.get(meta).isEatable();
	}

	public static byte getMaxStackSize(int meta) {
		return ims.get(meta).getMaxStackSize();
	}

	public static boolean canPlaceBlockWithMetadataAt(World world, int x, int y,
			int z, int meta) {
		return bms.get(meta).canPlaceBlockAt(world, x, y, z);
	}
}
