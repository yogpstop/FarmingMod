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
