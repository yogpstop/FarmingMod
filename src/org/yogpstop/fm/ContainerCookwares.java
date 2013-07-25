package org.yogpstop.fm;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.world.World;

public class ContainerCookwares extends Container {
	private World world;
	private int xCoord;
	private int yCoord;
	private int zCoord;
	private IInventory playerInventory;

	public ContainerCookwares(EntityPlayer player, World world, int x, int y, int z, int ID) {
		this.world = world;
		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
		this.playerInventory = player.inventory;
		addSlotToContainer(new Slot((IInventory) world.getBlockTileEntity(x, y, z), ID + 280, 8, 38));
		addSlotToContainer(new Slot((IInventory) world.getBlockTileEntity(x, y, z), ID + 290, 152, 38));
		if ((DB.isNeedFuel(((IInventory) world.getBlockTileEntity(x, y, z)).getStackInSlot(ID).getItemDamage()))) {
			addSlotToContainer(new Slot((IInventory) world.getBlockTileEntity(x, y, z), ID + 300, 80, 74));
		}
		for (int rows = 0; rows < 3; ++rows) {
			for (int slotIndex = 0; slotIndex < 9; ++slotIndex) {
				addSlotToContainer(new Slot((IInventory) world.getBlockTileEntity(x, y, z), ID + (((rows * 9) + slotIndex + 1) * 10), 8 + slotIndex * 18,
						96 + rows * 18));
			}
		}
		for (int rows = 0; rows < 3; ++rows) {
			for (int slotIndex = 0; slotIndex < 9; ++slotIndex) {
				addSlotToContainer(new Slot(this.playerInventory, slotIndex + rows * 9 + 9, 8 + slotIndex * 18, 164 + rows * 18));
			}
		}
		for (int slotIndex = 0; slotIndex < 9; ++slotIndex) {
			addSlotToContainer(new Slot(this.playerInventory, slotIndex, 8 + slotIndex * 18, 222));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {
		return this.world.getBlockId(this.xCoord, this.yCoord, this.zCoord) != FarmingMod.cookingTable.blockID ? false : entityPlayer.getDistanceSq(
				this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
	}

}
