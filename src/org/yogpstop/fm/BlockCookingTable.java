package org.yogpstop.fm;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockCookingTable extends Block {

	public BlockCookingTable(int i) {
		super(i, Material.iron);
		setCreativeTab(FarmingMod.tabCookwares);
		setUnlocalizedName("cookingTable");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer entityPlayer, int side, float par7, float par8,
			float par9) {
		if (!world.isRemote) {
			entityPlayer.openGui(FarmingMod.instance, -1, world, x, y, z);
		}
		return true;
	}
}
