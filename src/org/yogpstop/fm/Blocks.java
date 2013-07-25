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

public class Blocks extends Block {
	public Blocks(int id) {
		super(id, Material.plants);
		this.setTickRandomly(true);
		this.setCreativeTab(null);
		this.setHardness(0.0F);
		this.setStepSound(soundGrassFootstep);
		this.disableStats();
		this.setUnlocalizedName("");
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {}

	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
		return null;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int bID) {}

	@Override
	public int getRenderType() {
		return 0;
	}

	@Override
	public Icon getIcon(int side, int meta) {
		return null;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return true;// NP
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
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
		return false;// NP
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int idPicked(World par1World, int x, int y, int z) {
		return 0;
	}
}
