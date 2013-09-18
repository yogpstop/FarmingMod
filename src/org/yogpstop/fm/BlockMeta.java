/*
 * Copyright (C) 2012,2013 yogpstop
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the
 * GNU Lesser General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package org.yogpstop.fm;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockMeta {
	public static final BlockMeta defaultValue = new BlockMeta();

	private String name;
	// environment
	private ArrayList<Short> plantTo;
	private boolean hasWater;
	private boolean continuousWater;
	private byte minLight;
	private byte maxLight;
	// rendering
	private byte renderType;
	// drop items
	private ArrayList<Drop> drops;
	private byte pickedID;

	public BlockMeta() {
		// TODO
	}

	public String getName() {
		return this.name;
	}

	// countPer20sec// TODO
	public void updateTick(World world, int x, int y, int z, Random random) {}

	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> a = new ArrayList<ItemStack>();
		for (Drop d : this.drops) {
			a.add(d.getDrop((byte) world.getBlockMetadata(x, y, z), new Random()));
		}
		return a;
	}

	private boolean checkBlock(World world, int x, int y, int z, boolean place) {
		if (this.plantTo.contains(world.getBlockId(x, y - 1, z))
				&& ((world.getBlockMaterial(x, y - 1, z) != Material.water) || (world.getBlockMetadata(x, y - 1, z) == 0))
				&& (!(place ? this.hasWater : this.continuousWater) || (world.getBlockMaterial(x - 1, y - 1, z) == Material.water
						|| world.getBlockMaterial(x + 1, y - 1, z) == Material.water || world.getBlockMaterial(x, y - 1, z - 1) == Material.water || world
						.getBlockMaterial(x, y - 1, z + 1) == Material.water)) && this.minLight <= world.getBlockLightValue(x, y, z)
				&& world.getBlockLightValue(x, y, z) <= this.maxLight) { return true; }
		return false;
	}

	public boolean onNeighborBlockChange(World world, int x, int y, int z, int bID) {
		if (!checkBlock(world, x, y, z, false)) return false;
		return true;
	}

	public int getRenderType() {
		return this.renderType;
	}

	public Icon getIcon(int side, int meta) {
		return null;// TODO
	}

	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		if (checkBlock(world, x, y, z, true)) { return true; }
		return false;
	}

	public boolean canBlockStay(World world, int x, int y, int z) {
		if (checkBlock(world, x, y, z, false)) { return true; }
		return false;
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public int idPicked(World par1World, int x, int y, int z) {
		return this.pickedID;
	}

}
