package org.yogpstop.fm;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case -1:
			return new GuiCooking(player, world, x, y, z);
		default:
			return new GuiCookwares(player, world, x, y, z, ID);
		}
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case -1:
			return new ContainerCooking(player, world, x, y, z);
		default:
			return new ContainerCookwares(player, world, x, y, z, ID);
		}
	}

}
