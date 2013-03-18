package org.yogpstop.fm;

import net.minecraft.world.World;

public abstract class DB {
    private static NoNullTreeMap<String, Integer> items = new NoNullTreeMap<String, Integer>(-1);
    private static NoNullTreeMap<String, Integer> blocks = new NoNullTreeMap<String, Integer>(-1);
    protected static NoNullTreeMap<Integer, ItemMeta> ims = new NoNullTreeMap<Integer, ItemMeta>(ItemMeta.defaultValue);
    protected static NoNullTreeMap<Integer, BlockMeta> bms = new NoNullTreeMap<Integer, BlockMeta>(BlockMeta.defaultValue);

    private static int currentItemMetadata = 1;
    private static int currentBlockMetadata = 1;

    public static void addItem(ItemMeta im) {
        items.put(im.getUnlocalizedName(), currentItemMetadata);
        ims.put(currentItemMetadata, im);
        currentItemMetadata++;
    }

    public static void addBlock(BlockMeta bm) {
        blocks.put(bm.getName(), currentBlockMetadata);
        bms.put(currentBlockMetadata, bm);
        currentBlockMetadata++;
    }

    public static Integer getItem(String name) {
        return items.get(name);
    }

    public static Integer getBlock(String name) {
        return blocks.get(name);
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

    public static boolean canPlaceBlockWithMetadataAt(World world, int x, int y, int z, int meta) {
        return bms.get(meta).canPlaceBlockAt(world, x, y, z);
    }
}
