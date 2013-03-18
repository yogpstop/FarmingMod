package org.yogpstop.fm;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockMeta extends Block {
    // environment
    private ArrayList<Short> plantTo;
    private boolean hasWater;
    private boolean continuousWater;
    private byte minLight;
    private byte maxLight;
    // rendering
    private byte renderType;
    // growing
    private byte multiFrom;
    private byte maxGrow;
    private byte growRate;// 1=20s(1level)
    // drop items
    private ArrayList<Drop> drops;
    private byte pickedID;

    public BlockMeta(int par1, Material par3Material) {
        super(par1, par3Material);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1F, 0.25F, 1F);
        // TODO
    }

    // countPer20sec// TODO
    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {}

    @Override
    public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> a = new ArrayList<ItemStack>();
        for (Drop d : drops) {
            a.add(d.getDrop((byte) world.getBlockMetadata(x, y, z), new Random()));
        }
        return a;
    }

    private boolean checkBlock(World world, int x, int y, int z, boolean place) {
        if (plantTo.contains(world.getBlockId(x, y - 1, z))
                && ((world.getBlockMaterial(x, y - 1, z) != Material.water) || (world.getBlockMetadata(x, y - 1, z) == 0))
                && (!(place ? hasWater : continuousWater) || (world.getBlockMaterial(x - 1, y - 1, z) == Material.water
                        || world.getBlockMaterial(x + 1, y - 1, z) == Material.water || world.getBlockMaterial(x, y - 1, z - 1) == Material.water || world
                        .getBlockMaterial(x, y - 1, z + 1) == Material.water)) && minLight <= world.getBlockLightValue(x, y, z)
                && world.getBlockLightValue(x, y, z) <= maxLight) { return true; }
        return false;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int bID) {
        if (!checkBlock(world, x, y, z, false)) {
            dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
        }
    }

    @Override
    public int getRenderType() {
        return renderType;
    }

    @Override
    public Icon getBlockTextureFromSideAndMetadata(int side, int meta) {
        return null;// TODO
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        if (checkBlock(world, x, y, z, true)) { return true; }
        return false;
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z) {
        if (checkBlock(world, x, y, z, false)) { return true; }
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int idPicked(World par1World, int x, int y, int z) {
        return pickedID;
    }

}
