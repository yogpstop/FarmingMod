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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.world.World;

public class ContainerCooking extends Container {
	private World world;
	private int xCoord;
	private int yCoord;
	private int zCoord;
	private IInventory playerInventory;
	private EntityPlayer player;

	public ContainerCooking(EntityPlayer player, World world, int x, int y, int z) {
		this.world = world;
		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
		this.player = player;
		this.playerInventory = player.inventory;
		for (int slotIndex = 0; slotIndex < 5; ++slotIndex) {
			addSlotToContainer(new SlotCooking((IInventory) this.world.getBlockTileEntity(x, y, z), slotIndex, 8, 27 + slotIndex * 20));
		}
		for (int slotIndex = 0; slotIndex < 5; ++slotIndex) {
			addSlotToContainer(new SlotCooking((IInventory) this.world.getBlockTileEntity(x, y, z), slotIndex + 5, 152, 27 + slotIndex * 20));
		}
		for (int rows = 0; rows < 3; ++rows) {
			for (int slotIndex = 0; slotIndex < 9; ++slotIndex) {
				addSlotToContainer(new Slot(this.playerInventory, slotIndex + rows * 9 + 9, 8 + slotIndex * 18, 140 + rows * 18));
			}
		}
		for (int slotIndex = 0; slotIndex < 9; ++slotIndex) {
			addSlotToContainer(new Slot(this.playerInventory, slotIndex, 8 + slotIndex * 18, 198));
		}
	}

	public void onButtonPushed(int i) {
		if (((IInventory) this.world.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord)).getStackInSlot(i) == null) return;
		this.player.openGui(FarmingMod.instance, i, this.world, this.xCoord, this.yCoord, this.zCoord);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {
		return this.world.getBlockId(this.xCoord, this.yCoord, this.zCoord) != FarmingMod.cookingTable.blockID ? false : entityPlayer.getDistanceSq(
				this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
	}
}
